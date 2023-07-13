package com.jvel.edify.repository;

import com.jvel.edify.entity.Student;
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
    long updateFirstNameByEmailAddress(String firstName, String emailAddress);

    @Modifying
    @Transactional
    long deleteByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String emailAddress);

}
