package com.neo.service;

import java.util.List;

import com.neo.dto.SenhaDto;
import com.neo.dto.UsuarioDto;

public interface UsuarioService {
	
	String cadastrarUsuario(UsuarioDto usuarioDto);

	List<String> listarUsuarios();

	String atualizarUsuario(String novoUsuario, Long idUsuario);

	String atualizarSenha(SenhaDto senhaDto, Long idUsuario);

	String deletarUsuario(Long idUsuario);

}
