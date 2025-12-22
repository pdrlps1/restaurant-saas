package com.restaurant.saas.presentation.requests;

import com.restaurant.saas.domain.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class CreateUserRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String password;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String phone;

    @Size(max = 500, message = "URL do avatar deve ter no máximo 500 caracteres")
    private String avatarUrl;

    @NotEmpty(message = "Usuário deve ter pelo menos uma role")
    private Set<RoleType> roleTypes;


}
