package com.restaurant.saas.application.mappers;

import com.restaurant.saas.application.dto.UserDTO;
import com.restaurant.saas.domain.entities.Role;
import com.restaurant.saas.domain.entities.User;
import com.restaurant.saas.domain.enums.RoleType;
import com.restaurant.saas.presentation.requests.CreateUserRequest;
import com.restaurant.saas.presentation.requests.UpdateUserRequest;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não deve ser instanciada.");
    }

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .isActive(user.getIsActive())
                .emailVerified(user.getEmailVerified())
                .lastLogin(user.getLastLogin())
                .roles(user.getRoles() != null
                        ? user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
                        : Set.of())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User toEntity(CreateUserRequest request) {
        if (request == null) {
            return null;
        }

        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .avatarUrl(request.getAvatarUrl())
                .isActive(true)
                .emailVerified(false)
                .roles(Set.of())
                .build();
    }

    public static void updateEntity(User user, UpdateUserRequest request) {
        if (user == null || request == null) {
            return;
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        if (request.getIsActive() != null) {
            user.setIsActive(request.getIsActive());
        }
    }

    public static Set<Role> mapRoleTypesToRoles(
            Set<RoleType> roleTypes,
            Set<Role> availableRoles) {

        if (roleTypes == null || roleTypes.isEmpty()){
            return Set.of();
        }

        return availableRoles.stream()
                .filter(role -> roleTypes.contains(role.getName()))
                .collect(Collectors.toSet());
    }
}
