package com.talentosemissoes.identidade.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;
@Entity
public class Permissions {
    @Id
    UUID id;
    String code;
    String description;
}
