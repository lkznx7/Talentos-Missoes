package com.talentosemissoes.identidade.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class RolePermission {
    @Id
    UUID roleId;
    @Id
    UUID permissionId;

}
