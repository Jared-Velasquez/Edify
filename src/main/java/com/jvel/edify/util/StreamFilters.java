package com.jvel.edify.util;

import com.jvel.edify.entity.Student;
import com.jvel.edify.entity.User;
import com.jvel.edify.entity.roles.Role;

import java.util.function.Predicate;

public class StreamFilters {
    public static Predicate<User> byStudent = user -> (user.getRole() == Role.STUDENT);
}
