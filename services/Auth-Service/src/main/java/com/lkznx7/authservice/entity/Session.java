package com.lkznx7.authservice.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Session {

    UUID id;
    String token;
    String IpAddress;
    OffsetDateTime createdAt ;
    OffsetDateTime updatedAt ;

}
