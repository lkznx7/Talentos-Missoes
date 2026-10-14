package com.talentosemissoes.identidade.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Session {
    @Id
    UUID id;
    String refreshToken;
    OffsetDateTime expires_at;
    OffsetDateTime created_at;
    OffsetDateTime revoked_at;
    String ipAdress;
    String userAgent;
}
