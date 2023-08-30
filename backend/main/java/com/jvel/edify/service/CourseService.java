package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.*;
import com.jvel.edify.controller.requests.AssignmentCreateRequest;
import com.jvel.edify.controller.requests.ModuleCreateRequest;
import com.jvel.edify.controller.responses.AssignmentQueryMultipleResponse;
import com.jvel.edify.controller.responses.ModuleQueryMultipleResponse;
import com.jvel.edify.entity.*;
import com.jvel.edify.entity.Module;
import com.jvel.edify.entity.enums.Role;
import com.jvel.edify.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    public void addCourse(String title, Integer units, Integer teacherId) {
        // Check if teacher is present
        Optional<User> teacher = teacherRepository.findById(teacherId);
        if (teacher.isEmpty())
            throw new UserNotFoundException("User not found by id " + teacherId);
        if (teacher.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        // Check if course title is not taken
        boolean titleExists = courseRepository.existsByTitle(title);
        if (titleExists)
            throw new CourseAlreadyExistsException("Course already exists by title " + title);

        Course newCourse = Course.builder()
                .title(title)
                .units(units)
                .teacher((Teacher) teacher.get())
                .build();
        courseRepository.save(newCourse);
    }

    public void addCourse(String title, Integer units, String emailAddress) {
        // Check if teacher is present
        Optional<User> teacher = teacherRepository.findByEmailAddress(emailAddress);
        if (teacher.isEmpty())
            throw new UserNotFoundException("User not found by email address " + emailAddress);
        if (teacher.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by email address " + emailAddress);

        // Check if course title is not taken
        boolean titleExists = courseRepository.existsByTitle(title);
        if (titleExists)
            throw new CourseAlreadyExistsException("Course already exists by title " + title);

        Course newCourse = Course.builder()
                .title(title)
                .units(units)
                .teacher((Teacher) teacher.get())
                .build();
        courseRepository.save(newCourse);
    }

    public Course getCourse(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        return courseOptional.get();
    }

    public Teacher getTeacher(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        return courseOptional.get().getTeacher();
    }

    public List<Student> getStudents(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        return courseOptional.get().getStudents();
    }

    @Transactional
    public void addStudentToCourse(Long courseId, Integer studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> studentOptional = studentRepository.findById(studentId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (studentOptional.isEmpty())
            throw new UserNotFoundException("Student not found by id " + studentId);
        if (studentOptional.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        Course course = courseOptional.get();
        Student student = (Student) studentOptional.get();

        // Check if student has already been added to course

        Student checkStudent = course.getStudents()
                .stream()
                .filter(stu -> studentId.equals(stu.getId()))
                .findAny()
                .orElse(null);
        if (checkStudent != null)
            throw new IllegalStateException("Student already added to course");

        course.addStudents(student);
    }

    @Transactional
    public void addModuleToCourse(Integer teacherId, ModuleCreateRequest module) {
        Long courseId = module.getCourseId();
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("Teacher not found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        Course course = courseOptional.get();
        if (!course.getTeacher().getId().equals(teacherId))
            throw new UnauthorizedAccessException("User is not teacher of course " + courseId);

        List<Module> moduleList = course.getModules().stream().filter(element -> element.getTitle().equals(module.getTitle())).collect(Collectors.toList());
        if (!moduleList.isEmpty())
            throw new ModuleAlreadyExistsException("Module by name " + module.getTitle() + " already exists in course " + courseId);

        Module newModule = Module.builder()
                .title(module.getTitle())
                .course(course).build();

        moduleRepository.save(newModule);
    }

    @Transactional
    public void addAssignmentToModule(Integer teacherId, AssignmentCreateRequest assignment) {
        Integer moduleId = assignment.getModuleId();
        Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);
        if (moduleOptional.isEmpty())
            throw new ModuleNotFoundException("Module not found by id " + moduleId);
        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("Teacher not found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        Module module = moduleOptional.get();
        if (!module.getCourse().getTeacher().getId().equals(teacherId))
            throw new UnauthorizedAccessException("User is not teacher of course " + module.getCourse().getCourseId());

        List<Assignment> assignmentList = module.getAssignments().stream().filter(element -> element.getTitle().equals(assignment.getTitle())).collect(Collectors.toList());

        if (!assignmentList.isEmpty())
            throw new AssignmentAlreadyExistsException("Assignment by name " + assignment.getTitle() + " already exists in module " + module.getModuleId() + " of course " + module.getCourse().getCourseId());

        Assignment newAssignment = Assignment.builder()
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .dueAt(assignment.getDueAt())
                .unlockAt(assignment.getUnlockAt())
                .lockAt(assignment.getLockAt())
                .pointsPossible(assignment.getPointsPossible())
                .createdAt(Date.valueOf(LocalDate.now()))
                .visible(assignment.isVisible())
                .module(module).build();

        assignmentRepository.save(newAssignment);
    }

    public Assignment getAssignment(Integer userId, Integer assignmentId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (assignmentOptional.isEmpty())
            throw new AssignmentNotFoundException("Assignment not found by id " + assignmentId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + userId);

        // Check if user is teacher or student of this assignment
        User user = userOptional.get();
        Assignment assignment = assignmentOptional.get();
        Course course = assignment.getModule().getCourse();
        if (user.getRole().equals(Role.TEACHER) && !course.getTeacher().getId().equals(userId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());
        else if (user.getRole().equals(Role.STUDENT) && !course.getStudents().stream().anyMatch(student -> student.getId().equals(userId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());
        return assignment;
    }

    public Module getModule(Integer userId, Integer moduleId) {
        Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (moduleOptional.isEmpty())
            throw new ModuleNotFoundException("Module not found by id " + moduleId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + userId);

        // Check if user is teacher or student of this module
        User user = userOptional.get();
        Module module = moduleOptional.get();
        Course course = module.getCourse();
        if (user.getRole().equals(Role.TEACHER) && !course.getTeacher().getId().equals(userId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());
        else if (user.getRole().equals(Role.STUDENT) && !course.getStudents().stream().anyMatch(student -> student.getId().equals(userId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());
        return module;
    }

    public AssignmentQueryMultipleResponse getAssignments(Integer userId, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + userId);

        // Check if user is teacher or student of this assignment
        User user = userOptional.get();
        Course course = courseOptional.get();
        if (user.getRole().equals(Role.TEACHER) && !course.getTeacher().getId().equals(userId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());
        else if (user.getRole().equals(Role.STUDENT) && !course.getStudents().stream().anyMatch(student -> student.getId().equals(userId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());
        List<Assignment> assignments = new ArrayList<>();
        course.getModules().forEach(module -> {
           List<Assignment> assignmentsOfModule = module.getAssignments();
           assignments.addAll(assignmentsOfModule);
        });
        AssignmentQueryMultipleResponse response = AssignmentQueryMultipleResponse.builder()
                .assignments(assignments).build();
        return response;
    }

    public ModuleQueryMultipleResponse getModules(Integer userId, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + userId);

        // Check if user is teacher or student of this assignment
        User user = userOptional.get();
        Course course = courseOptional.get();
        if (user.getRole().equals(Role.TEACHER) && !course.getTeacher().getId().equals(userId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());
        else if (user.getRole().equals(Role.STUDENT) && !course.getStudents().stream().anyMatch(student -> student.getId().equals(userId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());
        List<Module> modules = course.getModules();
        ModuleQueryMultipleResponse response = ModuleQueryMultipleResponse.builder()
                .modules(modules).build();
        return response;
    }

    public void deleteAssignment(Integer teacherId, Integer assignmentId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);
        if (assignmentOptional.isEmpty())
            throw new AssignmentNotFoundException("Assignment not found by id " + assignmentId);
        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("Teacher not found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        Assignment assignment = assignmentOptional.get();

        if (!assignment.getModule().getCourse().getTeacher().getId().equals(teacherId))
            throw new UnauthorizedAccessException("User is not teacher of course " + assignment.getModule().getCourse().getCourseId());

        assignmentRepository.delete(assignment);
    }

    public void deleteModule(Integer teacherId, Integer moduleId) {
        Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);
        if (moduleOptional.isEmpty())
            throw new ModuleNotFoundException("Module not found by id " + moduleId);
        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("Teacher not found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        Module module = moduleOptional.get();

        if (!module.getCourse().getTeacher().getId().equals(teacherId))
            throw new UnauthorizedAccessException("User is not teacher of course " + module.getCourse().getCourseId());

        moduleRepository.delete(module);
    }
}
