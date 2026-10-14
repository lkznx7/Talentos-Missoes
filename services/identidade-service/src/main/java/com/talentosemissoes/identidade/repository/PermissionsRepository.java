package com.talentosemissoes.identidade.repository;

import com.talentosemissoes.identidade.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionsRepository extends JpaRepository<Permissions,UUID>{
}
