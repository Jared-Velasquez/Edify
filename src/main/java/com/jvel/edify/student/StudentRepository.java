package com.jvel.edify.student;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFirstName(String firstName);
    List<Student> findByLastName(String lastName);
    List<Student> findByFirstNameContaining(String firstName);
    List<Student> findByLastNameContaining(String lastName);

    List<Student> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Student> findByEmailAddress(String emailAddress);

    @Modifying
    @Transactional
    @Query(
            value = "update student_table set first_name = ?1 where email_address = ?2",
            nativeQuery = true
    )
    int updateFirstNameByEmailAddress(String firstName, String emailAddress);
}
