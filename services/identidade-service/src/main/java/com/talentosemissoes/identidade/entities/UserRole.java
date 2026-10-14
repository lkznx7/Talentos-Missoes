package com.talentosemissoes.identidade.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserRole implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAuth user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public UserRole() {}

    public UserRole(UserAuth user, Role role) {
        this.user = user;
        this.role = role;
    }

    public UserAuth getUser() {
        return user;
    }

    public void setUser(UserAuth user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(user, userRole.user) && Objects.equals(role, userRole.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
