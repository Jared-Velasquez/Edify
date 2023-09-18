package com.jvel.edify.service;

import com.jvel.edify.controller.exceptions.DepartmentNotFoundException;
import com.jvel.edify.controller.exceptions.MajorNotFoundException;
import com.jvel.edify.controller.exceptions.TeacherNotFoundException;
import com.jvel.edify.controller.exceptions.UserNotFoundException;
import com.jvel.edify.controller.responses.course_responses.CourseQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.teacher_responses.TeacherQueryMultipleResponse;
import com.jvel.edify.controller.responses.user_responses.teacher_responses.TeacherQueryResponse;
import com.jvel.edify.entity.Course;
import com.jvel.edify.entity.Teacher;
import com.jvel.edify.entity.User;
import com.jvel.edify.entity.enums.Department;
import com.jvel.edify.entity.enums.Position;
import com.jvel.edify.entity.enums.Role;
import com.jvel.edify.repository.CourseRepository;
import com.jvel.edify.repository.TeacherRepository;
import com.jvel.edify.util.StreamFilters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;

    public TeacherQueryMultipleResponse getAllTeachers() {
        List<User> teachers = teacherRepository.findAll().stream().filter(StreamFilters.byTeacher).collect(Collectors.toList());
        return TeacherQueryMultipleResponse.builder()
                .teachers(teachers)
                .build();
    }

    public TeacherQueryResponse getTeacherById(Integer teacherId) {
        Optional<User> teacher = teacherRepository.findById(teacherId);

        if (teacher.isEmpty())
            throw new UserNotFoundException("User not found by id " + teacherId);
        if (teacher.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        return TeacherQueryResponse.builder()
                .user(teacher.get())
                .build();
    }


    public CourseQueryMultipleResponse getCourses(Integer teacherId) {
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);

        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("User not found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("Teacher not found by id " + teacherId);

        List<Course> coursesList = courseRepository.findByTeacher((Teacher) teacherOptional.get());
        CourseQueryMultipleResponse courses = CourseQueryMultipleResponse.builder()
                .courses(coursesList)
                .build();
        return courses;
    }


    @Transactional
    public void updatePositionById(Integer teacherId, String position) {
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);

        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("No user found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("No teacher found by id " + teacherId);
        Position positionEnum = Position.valueOfLabel(position);
        if (positionEnum == null)
            throw new MajorNotFoundException("Position not found by " + position);

        Teacher teacher = (Teacher) teacherOptional.get();
        teacher.setPosition(positionEnum);
        teacherRepository.save(teacher);
    }

    @Transactional
    public void updateDepartmentById(Integer teacherId, String department) {
        Optional<User> teacherOptional = teacherRepository.findById(teacherId);

        if (teacherOptional.isEmpty())
            throw new UserNotFoundException("No user found by id " + teacherId);
        if (teacherOptional.get().getRole() != Role.TEACHER)
            throw new TeacherNotFoundException("No teacher found by id " + teacherId);
        Department departmentEnum = Department.valueOfLabel(department);
        if (departmentEnum == null)
            throw new DepartmentNotFoundException("Department not found by " + department);

        Teacher teacher = (Teacher) teacherOptional.get();
        teacher.setDepartment(departmentEnum);
        if (departmentEnum.equals(Department.COMPUTER_SCIENCE))
            teacher.setDepartmentalEmail(teacher.getUsername() + "@cs.edify.edu");
        else if (departmentEnum.equals(Department.CLASSICS))
            teacher.setDepartmentalEmail(teacher.getUsername() + "@humnet.edify.edu");
        else if (departmentEnum.equals(Department.ASIAN_AMERICAN_STUDIES))
            teacher.setDepartmentalEmail(teacher.getUsername() + "@asianam.edify.edu");
        else if (departmentEnum.equals(Department.MUSICOLOGY))
            teacher.setDepartmentalEmail(teacher.getUsername() + "@schoolofmusic.edify.edu");
        else if (departmentEnum.equals(Department.ELECTRICAL_ENGINEERING))
            teacher.setDepartmentalEmail(teacher.getUsername() + "@ee.edify.edu");
        else
            teacher.setDepartmentalEmail(teacher.getUsername() + "@edify.edu");
        teacherRepository.save(teacher);
    }
}
