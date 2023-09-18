package com.jvel.edify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jvel.edify.entity.enums.Gender;
import com.jvel.edify.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(
        name = "user_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="username_unique",
                        columnNames = "username"
                ),
                @UniqueConstraint(
                        name = "ssn_unique",
                        columnNames = "ssn"
                ),
                @UniqueConstraint(
                        name = "official_email_unique",
                        columnNames = "officialEmail"
                ),
                @UniqueConstraint(
                        name = "university_email_unique",
                        columnNames = "universityEmail"
                ),
                @UniqueConstraint(
                        name = "maildrop_unique",
                        columnNames = "maildrop"
                )
        }
)
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(
        name = "role",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(
            nullable = false
    )
    private String firstName;
    @Column(
            nullable = false
    )
    private String lastName;
    @Column(
            nullable = false
    )
    private String username;
    @Column(
            nullable = false
    )
    @JsonIgnore
    private Integer ssn;
    @Column(
            nullable = false
    )
    @JsonIgnore
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    @Enumerated(EnumType.STRING)
    @Column(
            insertable=false,
            updatable=false
    )
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String phoneNumber;
    private String officialEmail;
    private String universityEmail;
    private String maildrop;

    public User() {

    }

    public User(String firstName, String lastName, String username, Integer ssn, String password, Date dob, Role role, Gender gender, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.ssn = ssn;
        this.password = password;
        this.dob = dob;
        this.role = role;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.officialEmail = username + "@edify.edu";
        this.universityEmail = this.officialEmail;
        this.maildrop = this.officialEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
