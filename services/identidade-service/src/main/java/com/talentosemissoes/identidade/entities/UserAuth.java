package com.talentosemissoes.identidade.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class UserAuth implements UserDetails {
    @Id
    UUID id;
    String name;
    String email;
    String passwordHash;
    String status;
    OffsetDateTime created_at;
    OffsetDateTime updated_at;
    OffsetDateTime deleted_at;

    public UserAuth(UUID id, String name, String email, String passwordHash, String status) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.created_at = OffsetDateTime.now();
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }
    public UserAuth() {}

    // relacionamentos

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
