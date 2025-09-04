package com.neo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarClienteDto {

	private String nomeCliente;

	private String cpf;

	private LocalDate dataNascimento;

	private LocalDate dataCadastro;

}
