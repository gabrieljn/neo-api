package com.neo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.neo.domain.Cliente;
import com.neo.dto.AtualizarClienteDto;
import com.neo.dto.ClienteDto;
import com.neo.repository.ClienteRepository;
import com.neo.service.impl.ClienteServiceImpl;
import com.neo.service.mapper.ClienteMapper;

class ClienteServiceImplTest {

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ClienteMapper clienteMapper;

	@InjectMocks
	private ClienteServiceImpl clienteService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveCadastrarClienteComSucesso() {
		ClienteDto dto = new ClienteDto();
		dto.setNomeCliente("Maria");
		dto.setCpf("123.456.789-00");

		Cliente cliente = new Cliente();
		cliente.setNomeCliente("Maria");
		cliente.setCpf("123.456.789-00");

		when(clienteRepository.findByCpf(dto.getCpf())).thenReturn(null);
		when(clienteMapper.dtoToCliente(dto)).thenReturn(cliente);
		when(clienteRepository.save(cliente)).thenReturn(cliente);

		String resultado = clienteService.cadastrarCliente(dto);

		assertEquals("Cliente Maria cadastrado com sucesso", resultado);
		verify(clienteRepository, times(1)).save(cliente);
	}

	@Test
	void deveLancarExcecaoAoCadastrarClienteExistente() {
		ClienteDto dto = new ClienteDto();
		dto.setCpf("123.456.789-00");

		when(clienteRepository.findByCpf(dto.getCpf())).thenReturn(new Cliente());

		assertThrows(IllegalStateException.class, () -> clienteService.cadastrarCliente(dto));
		verify(clienteRepository, never()).save(any());
	}

	@Test
	void deveListarClientes() {
		when(clienteRepository.findAllClientes()).thenReturn(Arrays.asList("Maria", "João"));

		List<String> resultado = clienteService.listarClientes();

		assertEquals(2, resultado.size());
		assertTrue(resultado.contains("Maria"));
		verify(clienteRepository, times(1)).findAllClientes();
	}

	@Test
	void deveBuscarClientePorCpfComSucesso() {
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("Maria");
		cliente.setCpf("123.456.789-00");
		cliente.setDataNascimento(LocalDate.of(1990, 1, 1));

		ClienteDto dto = new ClienteDto();
		dto.setNomeCliente("Maria");
		dto.setCpf("123.456.789-00");
		dto.setDataNascimento(LocalDate.of(1990, 1, 1));

		when(clienteRepository.findByCpf("123.456.789-00")).thenReturn(cliente);
		when(clienteMapper.clienteToDto(eq(cliente))).thenReturn(dto);

		ClienteDto resultado = clienteService.buscarClienteCpf("123.456.789-00");

		assertNotNull(resultado);
		assertEquals("Maria", resultado.getNomeCliente());
		assertTrue(resultado.getIdade() > 0);
	}

	@Test
	void deveLancarExcecaoAoBuscarClientePorCpfNaoExistente() {
		when(clienteRepository.findByCpf("000.000.000-00")).thenReturn(null);

		assertThrows(NoSuchElementException.class, () -> clienteService.buscarClienteCpf("000.000.000-00"));
	}

	@Test
	void deveBuscarClientesPorNomeComSucesso() {
		Cliente cliente1 = new Cliente();
		cliente1.setNomeCliente("Maria");
		cliente1.setDataNascimento(LocalDate.of(1990, 1, 1));

		Cliente cliente2 = new Cliente();
		cliente2.setNomeCliente("Maria Clara");
		cliente2.setDataNascimento(LocalDate.of(1995, 5, 5));

		ClienteDto dto1 = new ClienteDto();
		dto1.setNomeCliente("Maria");
		dto1.setDataNascimento(cliente1.getDataNascimento());

		ClienteDto dto2 = new ClienteDto();
		dto2.setNomeCliente("Maria Clara");
		dto2.setDataNascimento(cliente2.getDataNascimento());

		when(clienteRepository.findAllByNomeClienteContaining("Maria")).thenReturn(Arrays.asList(cliente1, cliente2));
		when(clienteMapper.clienteToDto(eq(cliente1))).thenReturn(dto1);
		when(clienteMapper.clienteToDto(eq(cliente2))).thenReturn(dto2);

		List<ClienteDto> resultado = clienteService.buscarClienteNome("Maria");

		assertEquals(2, resultado.size());
		assertEquals("Maria", resultado.get(0).getNomeCliente());
		assertEquals("Maria Clara", resultado.get(1).getNomeCliente());
	}

	@Test
	void deveLancarExcecaoAoNaoEncontrarClientesPorNome() {
		when(clienteRepository.findAllByNomeClienteContaining("Inexistente")).thenReturn(Collections.emptyList());

		assertThrows(NoSuchElementException.class, () -> clienteService.buscarClienteNome("Inexistente"));
	}

	@Test
	void deveAtualizarClienteComSucesso() {
		Long id = 1L;
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("Maria");
		cliente.setCpf("123.456.789-00");

		AtualizarClienteDto dto = new AtualizarClienteDto();
		dto.setNomeCliente("Maria Atualizada");
		dto.setCpf("111.222.333-44");

		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
		when(clienteRepository.findByCpf(dto.getCpf())).thenReturn(null);
		when(clienteRepository.save(cliente)).thenReturn(cliente);

		String resultado = clienteService.atualizarCliente(dto, id);

		assertEquals("Cliente alterado com sucesso", resultado);
		verify(clienteRepository, times(1)).save(cliente);
	}

	@Test
	void atualizarClienteNaoEncontrado() {
		Long id = 1L;
		AtualizarClienteDto dto = new AtualizarClienteDto();

		when(clienteRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> clienteService.atualizarCliente(dto, id));
		verify(clienteRepository, never()).save(any());
	}

	@Test
	void deveDeletarClienteComSucesso() {
		Long id = 1L;
		Cliente cliente = new Cliente();
		cliente.setNomeCliente("Maria");

		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
		doNothing().when(clienteRepository).deleteById(id);

		String resultado = clienteService.deletarCliente(id);

		assertEquals("Cliente deletado com sucesso", resultado);
		verify(clienteRepository, times(1)).deleteById(id);
	}

	@Test
	void deletarClienteNaoEncontrado() {
		Long id = 1L;

		when(clienteRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> clienteService.deletarCliente(id));
		verify(clienteRepository, never()).deleteById(any());
	}

}