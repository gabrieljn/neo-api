package com.neo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.service.TokenService;
import com.neo.dto.UsuarioDto;
import com.neo.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/login")
@Tag(name = "Login", description = "Endpoint que realiza o login do usuário")
public class LoginController {

	private final LoginService loginService;

	private final TokenService tokenService;

	public LoginController(LoginService loginService, TokenService tokenService) {
		this.loginService = loginService;
		this.tokenService = tokenService;
	}

	@PostMapping
	@Operation(summary = "Realizar login do usuário", description = "Autentica o usuário e retorna um token JWT")
	@ApiResponse(responseCode = "200", description = "Login realizado com sucesso, retorna token")
	@ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
	@ApiResponse(responseCode = "401", description = "Usuário ou senha incorretos")
	@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	public ResponseEntity<?> login(@Valid @RequestBody UsuarioDto usuarioDto) {

		loginService.login(usuarioDto.getUsuario(), usuarioDto.getSenha());

		Map<String, String> payload = new HashMap<>();
		payload.put("usuario", usuarioDto.getUsuario());

		return ResponseEntity.ok(tokenService.gerarToken(payload, 40L));
	}

}