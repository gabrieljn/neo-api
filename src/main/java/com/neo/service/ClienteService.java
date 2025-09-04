package com.neo.service;

import java.time.LocalDate;
import java.util.List;

import com.neo.dto.AtualizarClienteDto;
import com.neo.dto.ClienteDto;

public interface ClienteService {

	public String cadastrarCliente(ClienteDto clienteDto);

	public List<String> listarClientes();

	public ClienteDto buscarClienteCpf(String cpf);
	
	public List<ClienteDto> buscarClienteNome(String nome);
	
	public String atualizarCliente(AtualizarClienteDto atualizarClienteDto, Long idCliente);

	public String deletarCliente(Long idCliente);

	public int calcularIdadeCliente(LocalDate dataNascimento);

}	