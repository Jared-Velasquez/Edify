package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.*;
import com.jvel.edify.controller.requests.course_requests.AnnouncementCreateRequest;
import com.jvel.edify.controller.requests.course_requests.AssignmentCreateRequest;
import com.jvel.edify.controller.requests.course_requests.ModuleCreateRequest;
import com.jvel.edify.controller.requests.course_requests.UpdateCourse;
import com.jvel.edify.controller.responses.course_responses.AnnouncementQueryMultipleResponse;
import com.jvel.edify.controller.responses.course_responses.AssignmentQueryMultipleResponse;
import com.jvel.edify.controller.responses.course_responses.AssignmentQueryResponse;
import com.jvel.edify.controller.responses.course_responses.ModuleQueryMultipleResponse;
import com.jvel.edify.entity.*;
import com.jvel.edify.entity.Module;
import com.jvel.edify.entity.enums.Role;
import com.jvel.edify.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private StudentAssignmentRepository studentAssignmentRepository;

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

    public Course getCourse(Integer userId, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + userId);

        // Check if user is teacher or student of this course
        User user = userOptional.get();
        Course course = courseOptional.get();
        if (user.getRole().equals(Role.TEACHER) && !course.getTeacher().getId().equals(userId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());
        else if (user.getRole().equals(Role.STUDENT) && course.getStudents().stream().noneMatch(student -> student.getId().equals(userId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());

        return course;
    }

    public Teacher getTeacher(Integer userId, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + userId);

        // Check if user is teacher or student of this course
        User user = userOptional.get();
        Course course = courseOptional.get();
        if (user.getRole().equals(Role.TEACHER) && !course.getTeacher().getId().equals(userId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());
        else if (user.getRole().equals(Role.STUDENT) && course.getStudents().stream().noneMatch(student -> student.getId().equals(userId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());

        return course.getTeacher();
    }

    public List<Student> getStudents(Integer userId, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + userId);

        // Check if user is teacher or student of this course
        User user = userOptional.get();
        Course course = courseOptional.get();
        if (user.getRole().equals(Role.TEACHER) && !course.getTeacher().getId().equals(userId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());
        else if (user.getRole().equals(Role.STUDENT) && course.getStudents().stream().noneMatch(student -> student.getId().equals(userId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());

        return course.getStudents();
    }

    @Transactional
    public void updateCode(Integer teacherId, Long courseId, String code) {
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

        // Check if this code already exists in the database
        Optional<Course> courseCodeOptional = courseRepository.findByCode(code);
        if (courseCodeOptional.isPresent())
            throw new CourseAlreadyExistsException("Course with code " + code + " already exists");

        course.setCode(code);
        courseRepository.save(course);
    }

    @Transactional
    public void updateSyllabus(Integer teacherId, Long courseId, String syllabus) {
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

        course.setSyllabusBody(syllabus);
        courseRepository.save(course);
    }

    @Transactional
    public void updateCourse(Integer teacherId, UpdateCourse courseParams) {
        Optional<Course> courseOptional = courseRepository.findById(courseParams.getCourseId());
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseParams.getCourseId());
        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("Teacher not found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        Course course = courseOptional.get();
        if (!course.getTeacher().getId().equals(teacherId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());

        if (courseParams.getTitle() != null && (courseParams.getTitle().length() == 0))
            throw new IllegalArgumentException("Title cannot be empty");
        if (courseParams.getCode() != null && (courseParams.getCode().length() == 0))
            throw new IllegalArgumentException("Code cannot be empty");
        if (courseParams.getSyllabusBody() != null && (courseParams.getSyllabusBody().length() == 0))
            throw new IllegalArgumentException("Syllabus cannot be empty");

        // If requested to change title
        if (courseParams.getTitle() != null) {
            if (courseRepository.existsByTitle(courseParams.getTitle()))
                throw new CourseAlreadyExistsException("Course with title " + courseParams.getTitle() + " already exists");
            course.setTitle(courseParams.getTitle());
        }

        // If requested to change code
        if (courseParams.getCode() != null) {
            if (courseRepository.existsByCode(courseParams.getCode()))
                throw new CourseAlreadyExistsException("Course with code " + courseParams.getCode() + " already exists");
            course.setCode(courseParams.getCode());
        }

        // If requested to change visibility
        if (courseParams.getPubliclyVisible() != null) {
            course.setPubliclyVisible(courseParams.getPubliclyVisible());
        }

        // If requested to change units
        if (courseParams.getUnits() != null) {
            course.setUnits(courseParams.getUnits());
        }

        // If requested to change syllabus
        if (courseParams.getSyllabusBody() != null) {
            course.setSyllabusBody(courseParams.getSyllabusBody());
        }
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
        course.getAssignments().forEach((assignment) -> {
            StudentAssignment sa = new StudentAssignment();
            sa.setAssignment(assignment);
            sa.setStudent(student);
            assignment.addStudent(sa);
            student.addAssignment(sa);

            studentAssignmentRepository.save(sa);
        });
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
        Course course = module.getCourse();
        if (!course.getTeacher().getId().equals(teacherId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());

        List<Assignment> assignmentList = module.getAssignments().stream().filter(element -> element.getTitle().equals(assignment.getTitle())).toList();

        if (!assignmentList.isEmpty())
            throw new AssignmentAlreadyExistsException("Assignment by name " + assignment.getTitle() + " already exists in module " + module.getModuleId() + " of course " + module.getCourse().getCourseId());

        Assignment newAssignment = Assignment.builder()
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .dueAt(assignment.getDueAt())
                .unlockAt(assignment.getUnlockAt())
                .lockAt(assignment.getLockAt())
                .pointsPossible(assignment.getPointsPossible())
                .createdAt(new Date())
                .visible(assignment.isVisible())
                .module(module).build();

        assignmentRepository.save(newAssignment);

        //System.out.println(course.getStudents());

        course.getStudents().forEach((student) -> {
            if (student.getId() != null) {
                StudentAssignment sa = new StudentAssignment();
                sa.setAssignment(newAssignment);
                sa.setStudent(student);
                newAssignment.addStudent(sa);
                student.addAssignment(sa);

                studentAssignmentRepository.save(sa);
            } else {
                //System.out.println("student = " + student);
            }
            //System.out.println("student = " + student);
        });
    }

    @Transactional
    public void addAnnouncementToCourse(Integer teacherId, AnnouncementCreateRequest announcement) {
        Long courseId = announcement.getCourseId();
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
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());

        List<Announcement> announcementList = course.getAnnouncements().stream().filter(element -> element.getTitle().equals(announcement.getTitle())).toList();
        if (!announcementList.isEmpty())
            throw new AnnouncementAlreadyExistsException("Announcement by name " + announcement.getTitle() + " already exists in course " + course.getCourseId());

        Announcement newAnnouncement = Announcement.builder()
                .title(announcement.getTitle())
                .description(announcement.getDescription())
                .createdAt(new Date())
                .course(course).build();

        announcementRepository.save(newAnnouncement);
    }

    public AssignmentQueryResponse getAssignment(Integer userId, Integer assignmentId) {
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

        AssignmentQueryResponse response = AssignmentQueryResponse.builder()
                .assignment(assignment).build();
        return response;
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

    public AnnouncementQueryMultipleResponse getAnnouncements(Integer userId, Long courseId) {
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
        else if (user.getRole().equals(Role.STUDENT) && course.getStudents().stream().noneMatch(student -> student.getId().equals(userId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());

        return AnnouncementQueryMultipleResponse.builder()
                .announcements(course.getAnnouncements()).build();
    }

    @Transactional
    public void deleteStudent(Integer teacherId, Integer studentId, Long courseId) {
        // TODO: Implement delete student along with its reference in the course and the student_assignment rows
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> userOptional = userRepository.findById(teacherId);
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (courseOptional.isEmpty())
            throw new CourseNotFoundException("Course not found by id " + courseId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + teacherId);
        if (studentOptional.isEmpty())
            throw new UserNotFoundException("Student not found by id " + studentId);

        // Check if user is teacher or student of this assignment
        User user = userOptional.get();
        Course course = courseOptional.get();
        User userStudent = studentOptional.get();
        if (user.getRole().equals(Role.TEACHER) && !course.getTeacher().getId().equals(teacherId))
            throw new UnauthorizedAccessException("User is not teacher of course " + course.getCourseId());
        if (userStudent.getRole().equals(Role.STUDENT) && course.getStudents().stream().noneMatch(student -> student.getId().equals(studentId)))
            throw new UnauthorizedAccessException("User is not student of course " + course.getCourseId());

        Student student = (Student) userStudent;
        // Remove assignment mappings
        List<StudentAssignment> studentAssignments = student.getStudentAssignments().stream().filter(sa -> sa.getAssignment().getModule().getCourse().equals(course)).toList();
        studentAssignments.forEach((sa) -> {
            if (sa.getStudent() != null && sa.getAssignment() != null) {
                Assignment assignment = sa.getAssignment();
                assignment.getStudentAssignments().removeIf((sa_assignment) -> sa_assignment.getStudent().equals(student));
                student.getStudentAssignments().removeIf((sa_student) -> sa_student.getAssignment().equals(assignment));
                studentRepository.save(student);
                assignmentRepository.save(assignment);
                sa.setStudent(null);
                sa.setAssignment(null);
            }
        });
        studentAssignmentRepository.deleteAll(studentAssignments);
        // Remove from lists
        student.getCourses().remove(course);
        course.getStudents().remove(student);
    }

    @Transactional
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

    @Transactional
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
