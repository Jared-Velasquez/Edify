package com.jvel.edify.util;

import com.jvel.edify.entity.User;
import com.jvel.edify.entity.enums.Role;

import java.util.function.Predicate;

public class StreamFilters {
    public static Predicate<User> byStudent = user -> (user.getRole() == Role.STUDENT);
    public static Predicate<User> byTeacher = user -> (user.getRole() == Role.TEACHER);
}
