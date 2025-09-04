package com.neo.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

	@NotBlank(message = "Nome do cliente é obrigatório")
	@Schema(description = "Nome completo do cliente", example = "Gabriel Alves")
	private String nomeCliente;

	@NotBlank(message = "CPF é obrigatório")
	@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido. Formato correto: 000.000.000-00")
	@Schema(description = "CPF do cliente", example = "123.456.789-00")
	private String cpf;

	@NotNull(message = "Data de nascimento é obrigatória")
	@Schema(description = "Data de nascimento do cliente", example = "1990-05-01")
	private LocalDate dataNascimento;

	@NotNull(message = "Data de cadastro é obrigatória")
	@Schema(description = "Data de cadastro do cliente", example = "2025-03-09")
	private LocalDate dataCadastro;
	
    @Schema(description = "Idade do cliente", example = "35")
	private int idade;
}