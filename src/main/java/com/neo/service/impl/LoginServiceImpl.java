package com.neo.service.impl;

import java.util.NoSuchElementException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.neo.domain.Usuario;
import com.neo.exception.InvalidPasswordException;
import com.neo.repository.UsuarioRepository;
import com.neo.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private UsuarioRepository usuarioRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public LoginServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public void login(String usuarioLogin, String senha) {
		Usuario usuario = usuarioRepository.findByUsuario(usuarioLogin);

		if (usuario == null) {

			throw new NoSuchElementException("Usuário não encontrado");

		}

		if (bCryptPasswordEncoder.matches(senha, usuario.getSenha()) == false) {

			throw new InvalidPasswordException("Senha informada está incorreta");

		}

	}

}
