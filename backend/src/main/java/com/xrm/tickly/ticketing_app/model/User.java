package com.xrm.tickly.ticketing_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.xrm.tickly.ticketing_app.enums.UserRole;
import com.xrm.tickly.ticketing_app.validation.StrongPassword;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@ToString(exclude = {"projects", "assignedTickets", "reportedTickets", "comments"})
@EqualsAndHashCode(exclude = {"projects", "assignedTickets", "reportedTickets", "comments"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", 
            message = "Username can only contain letters, numbers, dots, underscores, and hyphens")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @StrongPassword  
    private String password;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'USER'")
    private UserRole role = UserRole.USER;  

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;   

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,}$", message = "Invalid phone number format")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void incrementFailedLoginAttempts() {
        this.failedLoginAttempts++;
        if (this.failedLoginAttempts >= 5) {
            this.isActive = false;
        }
    }

    public void resetFailedLoginAttempts() {
        this.failedLoginAttempts = 0;
    }

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public boolean isAdmin() {
        return UserRole.ADMIN.equals(this.role);
    }

    @OneToMany(mappedBy = "assignee")
    private Set<Ticket> assignedTickets = new HashSet<>();

    @OneToMany(mappedBy = "reporter")
    private Set<Ticket> reportedTickets = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Project> projects = new HashSet<>();
}