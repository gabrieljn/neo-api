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
public class SenhaDto {

    @NotBlank(message = "Senha atual é obrigatória")
    @Schema(description = "Senha atual do usuário", example = "123456")
    private String senhaAtual;

    @NotBlank(message = "Nova senha é obrigatória")
    @Schema(description = "Nova senha do usuário", example = "654321")
    private String novaSenha;
}