package com.jvel.edify.repository;

import com.jvel.edify.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByFirstNameContaining(String firstName);
    List<User> findByLastNameContaining(String lastName);
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<User> findByEmailAddress(String emailAddress);
    Optional<User> findBySsn(Integer ssn);

    @Modifying
    @Transactional
    @Query(
            value = "update User set firstName = ?1 where emailAddress = ?2"
    )
    void updateFirstNameByEmailAddress(String firstName, String emailAddress);

    @Modifying
    @Transactional
    void deleteByEmailAddress(String emailAddress);
    @Modifying
    @Transactional
    void deleteBySsn(Integer ssn);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsBySsn(Integer ssn);


}
