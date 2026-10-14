package com.talentosemissoes.identidade.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class UserRole {
    @Id
    UUID userId;
    UUID roleId;

    public UserRole() {

    }

}
