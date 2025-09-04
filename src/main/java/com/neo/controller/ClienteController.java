package com.neo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.dto.AtualizarClienteDto;
import com.neo.dto.ClienteDto;
import com.neo.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/clientes")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente no sistema")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<String> cadastrarCliente(@Valid @RequestBody ClienteDto clienteDto) {
        String resultado = clienteService.cadastrarCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Retorna a lista de todos os clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<List<String>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF", description = "Retorna os dados de um cliente específico pelo CPF")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public ResponseEntity<ClienteDto> buscarClienteCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clienteService.buscarClienteCpf(cpf));
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar clientes por nome", description = "Retorna todos os clientes que contenham o nome informado")
    @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso")
    @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado com o nome informado")
    public ResponseEntity<List<ClienteDto>> buscarClientesNome(@RequestBody String nome) {
        return ResponseEntity.ok(clienteService.buscarClienteNome(nome));
    }

    @PutMapping("/{idCliente}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente pelo ID")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<String> atualizarCliente(@Valid @RequestBody AtualizarClienteDto atualizarClienteDto, @PathVariable Long idCliente) {
        String resultado = clienteService.atualizarCliente(atualizarClienteDto, idCliente);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{idCliente}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema pelo ID")
    @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso")
    @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<String> deletarCliente(@PathVariable Long idCliente) {
        clienteService.deletarCliente(idCliente);
        return ResponseEntity.ok("Cliente deletado com sucesso");
    }
}