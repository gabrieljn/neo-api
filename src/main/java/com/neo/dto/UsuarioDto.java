package com.neo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {


    @NotBlank(message = "Usuário é obrigatório")
    @Schema(description = "Nome do usuário", example = "gabriel123")
    private String usuario;

    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

}