package com.restaurant.saas.application.dto;

import com.restaurant.saas.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String avatarUrl;
    private Boolean isActive;
    private Boolean emailVerified;
    private LocalDateTime lastLogin;
    private Set<RoleType> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
