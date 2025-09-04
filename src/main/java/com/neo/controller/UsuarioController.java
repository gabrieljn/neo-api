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

import com.neo.dto.SenhaDto;
import com.neo.dto.UsuarioDto;
import com.neo.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuários", description = "Endpoints para gerenciar usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo usuário", description = "Método responsável por cadastrar novos usuários.")
    @ApiResponse(responseCode = "201", description = "Usuário criado")
    @ApiResponse(responseCode = "400", description = "Parâmetro não informado ou inválido")
    @ApiResponse(responseCode = "406", description = "Usuário já existente")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<String> cadastrarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        String resultado = usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Busca e lista todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Retorna lista de usuários")
    @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<List<String>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("/{idUsuario}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza o usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado")
    @ApiResponse(responseCode = "400", description = "Parâmetro não informado ou inválido")
    @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<String> atualizarUsuario(@Valid @RequestBody String novoUsuario, @PathVariable Long idUsuario) {
        String resultado = usuarioService.atualizarUsuario(novoUsuario, idUsuario);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{idUsuario}/senha")
    @Operation(summary = "Atualizar senha", description = "Atualiza a senha de um usuário")
    @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Parâmetro não informado ou senha inválida")
    @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<String> atualizarSenha(@Valid @RequestBody SenhaDto senhaDto, @PathVariable Long idUsuario) {
        String resultado = usuarioService.atualizarSenha(senhaDto, idUsuario);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{idUsuario}")
    @Operation(summary = "Deletar usuário", description = "Deleta um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário deletado")
    @ApiResponse(responseCode = "400", description = "Parâmetro não informado")
    @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long idUsuario) {
        String resultado = usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.ok(resultado);
    }
}