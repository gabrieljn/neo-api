package com.neo.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.neo.domain.Usuario;
import com.neo.dto.SenhaDto;
import com.neo.dto.UsuarioDto;
import com.neo.exception.InvalidPasswordException;
import com.neo.repository.UsuarioRepository;
import com.neo.service.UsuarioService;
import com.neo.service.mapper.UsuarioMapper;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioMapper usuarioMapper;
	private UsuarioRepository usuarioRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UsuarioServiceImpl(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.usuarioMapper = usuarioMapper;
		this.usuarioRepository = usuarioRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public String cadastrarUsuario(UsuarioDto usuarioDto) {
		if(usuarioRepository.findByUsuario(usuarioDto.getUsuario()) != null) {
			throw new IllegalStateException("Usuário já existente");
		}
		usuarioDto.setSenha(bCryptPasswordEncoder.encode(usuarioDto.getSenha()));
		Usuario novoUsuario = usuarioMapper.dtoToUsuario(usuarioDto);
		usuarioRepository.save(novoUsuario);
		return "Usuário " + novoUsuario.getUsuario() + " cadastrado com sucesso" ;
	}

	public List<String> listarUsuarios() {
		return usuarioRepository.findAllUsuarios();
	}

	public String atualizarUsuario(String novoUsuario, Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		if(usuario == null ) {
			throw new NoSuchElementException("Usuário não encontrado");
		}
		usuario.setUsuario(novoUsuario);
		usuarioRepository.save(usuario);
		return "Usuário alterado com sucesso";
	}
	
	public String atualizarSenha(SenhaDto senhaDto, Long idUsuario)  {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		if(usuario == null ) {
			throw new NoSuchElementException("Usuário não encontrado");
		}
		if(bCryptPasswordEncoder.matches(senhaDto.getSenhaAtual(),usuario.getSenha()) == false) {
			throw new InvalidPasswordException("Senha informada está incorreta");
		}
		usuario.setSenha(bCryptPasswordEncoder.encode(senhaDto.getNovaSenha()));
		usuarioRepository.save(usuario);
		return "Senha alterada com sucesso";
	}

	public String deletarUsuario(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		if(usuario == null ) {
			throw new NoSuchElementException("Usuário não encontrado");
		}
		usuarioRepository.deleteById(idUsuario);
		return "Usuário deletado com sucesso";
	}
	
}