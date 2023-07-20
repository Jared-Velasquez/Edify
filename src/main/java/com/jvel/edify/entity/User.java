package com.jvel.edify.entity;

import com.jvel.edify.entity.roles.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(
        name = "user_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="email_address_unique",
                        columnNames = "emailAddress"
                ),
                @UniqueConstraint(
                        name = "ssn_unique",
                        columnNames = "ssn"
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
    private String firstName;
    private String lastName;
    @Column(
            nullable = false
    )
    private String emailAddress;
    @Column(
            nullable = false
    )
    private Integer ssn;
    @Column(
            nullable = false
    )
    private String password;
    private Date dob;
    @Enumerated(EnumType.STRING)
    @Column(
            insertable=false,
            updatable=false
    )
    private Role role;

    public User() {

    }

    public User(String firstName, String lastName, String emailAddress, Integer ssn, String password, Date dob, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.ssn = ssn;
        this.password = password;
        this.dob = dob;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return emailAddress;
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
