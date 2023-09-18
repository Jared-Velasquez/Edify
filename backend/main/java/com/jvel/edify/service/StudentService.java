package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.*;
import com.jvel.edify.controller.responses.course_responses.*;
import com.jvel.edify.controller.responses.user_responses.student_responses.ScoreQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.student_responses.ScoreQueryResponse;
import com.jvel.edify.controller.responses.user_responses.student_responses.StudentQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.student_responses.StudentQueryResponse;
import com.jvel.edify.entity.*;
import com.jvel.edify.entity.enums.Major;
import com.jvel.edify.entity.enums.Role;
import com.jvel.edify.repository.*;
import com.jvel.edify.util.StreamFilters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    public void addStudent(Student student) {
        // Check if username is present
        if (student.getUsername() == null)
            throw new IllegalStateException("Username is not present");

        // Check if SSN is present
        if (student.getSsn() == null)
            throw new IllegalStateException("SSN is not present");

        // Check if another user has the same username as this student
        Optional<User> userOptional = userRepository.findByUsername(student.getUsername());
        if (userOptional.isPresent())
            throw new UserAlreadyExistsException("Username already taken by user");

        // Check if another student has the same SSN as this student
        Optional<User> userOptionalSSN = userRepository.findBySsn(student.getSsn());
        if (userOptionalSSN.isPresent())
            throw new UserAlreadyExistsException("SSN already taken by user");

        studentRepository.save(student);
    }

    public StudentQueryMultipleResponse getAllStudents() {
        List<User> students = studentRepository.findAll().stream().filter(StreamFilters.byStudent).collect(Collectors.toList());
        return StudentQueryMultipleResponse.builder()
                .students(students)
                .build();
    }

    public StudentQueryResponse getStudentById(Integer studentId) {
        Optional<User> student = studentRepository.findById(studentId);

        if (student.isEmpty())
            throw new UserNotFoundException("User not found by id " + studentId);
        if (student.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        return StudentQueryResponse.builder()
                .user(student.get())
                .build();
    }

    public CourseTeacherMultipleResponse getCourses(Integer studentId) {
        Optional<User> user = studentRepository.findById(studentId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by id " + studentId);
        if (user.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        Student student = (Student) user.get();

        List<CourseTeacherResponse> courses = student.getCourses().stream().map((course) -> CourseTeacherResponse.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .code(course.getCode())
                .syllabusBody(course.getSyllabusBody())
                .publiclyVisible(course.isPubliclyVisible())
                .units(course.getUnits())
                .courseContent(course.getCourseContent())
                .modules(course.getModules())
                .announcements(course.getAnnouncements())
                .firstName(course.getTeacher().getFirstName())
                .lastName(course.getTeacher().getLastName())
                .position(course.getTeacher().getPosition())
                .department(course.getTeacher().getDepartment())
                .build()).toList();

        return CourseTeacherMultipleResponse.builder()
                .courses(courses)
                .build();
    }

    public AssignmentCourseQueryMultipleResponse getAssignments(Integer studentId) {
        Optional<User> user = studentRepository.findById(studentId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by id " + studentId);
        if (user.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        Student student = (Student) user.get();

        List<AssignmentCourseQueryResponse> assignments = new ArrayList<>();
        student.getStudentAssignments().forEach((sa) -> {
            Long courseId = sa.getAssignment().getModule().getCourse().getCourseId();
            assignments.add(AssignmentCourseQueryResponse.builder()
                    .assignment(sa.getAssignment())
                    .courseId(courseId).build());
        });
        return AssignmentCourseQueryMultipleResponse.builder().assignments(assignments).build();
    }

    public ScoreQueryMultipleResponse getScores(Integer studentId) {
        Optional<User> user = studentRepository.findById(studentId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by id " + studentId);
        if (user.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        Student student = (Student) user.get();

        List<ScoreQueryResponse> assignments = new ArrayList<>();
        student.getStudentAssignments().forEach((sa) -> {
            assignments.add(ScoreQueryResponse.builder()
                    .assignmentId(sa.getAssignment().getAssignmentId())
                    .score(sa.getPoints()).build());
        });
        return ScoreQueryMultipleResponse.builder()
                .assignments(assignments).build();
    }

    public CourseTeacherMultipleResponse getUnenrolledCourses(Integer studentId) {
        Optional<User> user = studentRepository.findById(studentId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by id " + studentId);
        if (user.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        Student student = (Student) user.get();

        List<Course> studentCourses = student.getCourses();
        List<CourseTeacherResponse> courses = courseRepository.findAll().stream().filter((course) -> {
            for (Course studentCourse : studentCourses) {
                if (studentCourse.getCourseId().equals(course.getCourseId()))
                    return false;
            }
            return true;
        }).map((course) -> CourseTeacherResponse.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .code(course.getCode())
                .syllabusBody(course.getSyllabusBody())
                .publiclyVisible(course.isPubliclyVisible())
                .units(course.getUnits())
                .courseContent(course.getCourseContent())
                .modules(course.getModules())
                .announcements(course.getAnnouncements())
                .firstName(course.getTeacher().getFirstName())
                .lastName(course.getTeacher().getLastName())
                .position(course.getTeacher().getPosition())
                .department(course.getTeacher().getDepartment())
                .build()).toList();

        return CourseTeacherMultipleResponse.builder()
                .courses(courses)
                .build();
    }

    @Transactional
    public void updateScore(Integer studentId, Integer score, Integer assignmentId) {
        Optional<User> user = studentRepository.findById(studentId);
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found by id " + studentId);
        if (assignmentOptional.isEmpty())
            throw new AssignmentNotFoundException("Assignment not found by id " + assignmentId);
        if (user.get().getRole() != Role.STUDENT)
            throw new StudentNotFoundException("Student not found by id " + studentId);

        Student student = (Student) user.get();
        Assignment assignment = assignmentOptional.get();
        StudentAssignment sa = assignment.getStudentAssignments().stream().filter(element -> element.getStudent().equals(student)).findAny().orElse(null);
        if (sa == null)
            throw new StudentNotFoundException("Student not found by id " + studentId + " in assignment " + assignmentId);
        sa.setPoints(score);
    }

    @Transactional
    public void updateMajorById(Integer studentId, String major) {
        Optional<User> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isEmpty())
            throw new UserNotFoundException("No user found by id " + studentId);
        if (studentOptional.get().getRole() != Role.STUDENT)
            throw new TeacherNotFoundException("No student found by id " + studentId);
        Major majorEnum = Major.valueOfLabel(major);
        if (majorEnum == null)
            throw new MajorNotFoundException("Major not found by " + major);

        Student student = (Student) studentOptional.get();
        student.setMajor(majorEnum);
        studentRepository.save(student);
    }
}
