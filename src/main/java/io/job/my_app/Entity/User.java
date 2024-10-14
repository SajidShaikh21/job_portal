package io.job.my_app.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.job.my_app.Entity.Security.Roles;
import io.job.my_app.Entity.Security.UserRole;
import io.job.my_app.dto.UserDto;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;




@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String status;

    @Column(length = 45)
    private String address;

    @Column(length = 45)
    private String email;

    @Column(length = 45)
    private String moNumber;

    private String password;

    private String confirmPassword;

    @Column(length = 45)
    private String role;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JobPost> jobPosts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Resume> resumes;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Identity identity;

    public User(UserDto userDto) {
        this.userId = userDto.getUserId();
        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.address = userDto.getAddress();
        this.moNumber = userDto.getMoNumber();
        this.password = userDto.getPassword();
        this.status = userDto.getStatus();
    }


    public User(String email, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Set<UserRole> roles, Integer userId) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.userRoles = roles;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> set = new HashSet<>();

        for (UserRole userRole : this.userRoles) {
            Roles role = userRole.getRole();
            set.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        return set;
    }

    @Override
    public String getUsername() {
        return this.email;
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