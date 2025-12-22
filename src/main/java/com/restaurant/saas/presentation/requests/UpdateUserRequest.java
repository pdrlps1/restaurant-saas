package com.restaurant.saas.presentation.requests;

import com.restaurant.saas.domain.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    private String name;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String password;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String phone;

    @Size(max = 500, message = "URL do avatar deve ter no máximo 500 caracteres")
    private String avatarUrl;

    private Boolean isActive;

    private Set<RoleType> roleTypes;
}
