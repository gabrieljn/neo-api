package com.neo.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.neo.domain.Cliente;
import com.neo.dto.AtualizarClienteDto;
import com.neo.dto.ClienteDto;
import com.neo.repository.ClienteRepository;
import com.neo.service.ClienteService;
import com.neo.service.mapper.ClienteMapper;

@Service
public class ClienteServiceImpl implements ClienteService {

	private ClienteMapper clienteMapper;

	private ClienteRepository clienteRepository;

	public ClienteServiceImpl(ClienteMapper clienteMapper, ClienteRepository clienteRepository) {
		super();
		this.clienteMapper = clienteMapper;
		this.clienteRepository = clienteRepository;
	}

	public String cadastrarCliente(ClienteDto clienteDto) {

		if (clienteRepository.findByCpf(clienteDto.getCpf()) != null) {

			throw new IllegalStateException("Cliente já existente");

		}

		Cliente novoCliente = clienteMapper.dtoToCliente(clienteDto);	
		clienteRepository.save(novoCliente);
		return "Cliente " + novoCliente.getNomeCliente() + " cadastrado com sucesso";
	}

	public List<String> listarClientes() {
		return clienteRepository.findAllClientes();
	}

	public ClienteDto buscarClienteCpf(String cpf) {
		Cliente cliente = clienteRepository.findByCpf(cpf);
		ClienteDto clienteRetorno = new ClienteDto();

		if (cliente == null) {

			throw new NoSuchElementException("Cliente não encontrado");

		}
		clienteRetorno = clienteMapper.clienteToDto(cliente);
		clienteRetorno.setIdade(calcularIdadeCliente(clienteRetorno.getDataNascimento()));
		return clienteRetorno;
	}
	
	public List<ClienteDto> buscarClienteNome(String nome) {
	    List<Cliente> clientes = clienteRepository.findAllByNomeClienteContaining(nome);

	    if (clientes.isEmpty()) {
	        throw new NoSuchElementException("Nenhum cliente encontrado com o nome: " + nome);
	    }

	    return clientes.stream()
	            .map(cliente -> {
	                ClienteDto dto = new ClienteDto();
	                dto = clienteMapper.clienteToDto(cliente);
	                dto.setIdade(calcularIdadeCliente(dto.getDataNascimento()));
	                return dto;
	            })
	            .toList();
	}

	public String atualizarCliente(AtualizarClienteDto atualizarClienteDto, Long idCliente) {
		Cliente cliente = clienteRepository.findById(idCliente).orElse(null);

		if (cliente == null) {

			throw new NoSuchElementException("Cliente não encontrado");
		}

		if (clienteRepository.findByCpf(atualizarClienteDto.getCpf()) != null) {

			throw new IllegalStateException("CPF já cadastrado");

		}

		clienteMapper.dtoToCliente(atualizarClienteDto, cliente);
		clienteRepository.save(cliente);
		return "Cliente alterado com sucesso";
	}

	public String deletarCliente(Long idCliente) {
		Cliente cliente = clienteRepository.findById(idCliente).orElse(null);

		if (cliente == null) {

			throw new NoSuchElementException("Cliente não encontrado");

		}

		clienteRepository.deleteById(idCliente);
		return "Cliente deletado com sucesso";
	}

	public int calcularIdadeCliente(LocalDate dataNascimento) {
		return Period.between(dataNascimento, LocalDate.now()).getYears();
	}

}
