package com.neo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.neo.domain.Usuario;
import com.neo.dto.SenhaDto;
import com.neo.dto.UsuarioDto;
import com.neo.exception.InvalidPasswordException;
import com.neo.repository.UsuarioRepository;
import com.neo.service.impl.UsuarioServiceImpl;
import com.neo.service.mapper.UsuarioMapper;

public class UsuarioServiceImplTest {


    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        UsuarioDto dto = new UsuarioDto();
        dto.setUsuario("gabriel");
        dto.setSenha("123456");

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsuario("gabriel");

        when(usuarioRepository.findByUsuario(dto.getUsuario())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(dto.getSenha())).thenReturn("senhaHash");
        when(usuarioMapper.dtoToUsuario(dto)).thenReturn(novoUsuario);
        when(usuarioRepository.save(novoUsuario)).thenReturn(novoUsuario);

        String resultado = usuarioService.cadastrarUsuario(dto);

        assertEquals("Usuário gabriel cadastrado com sucesso", resultado);
        verify(usuarioRepository, times(1)).save(novoUsuario);
    }

    @Test
    void naoDeveCadastrarUsuarioExistente() {
        UsuarioDto dto = new UsuarioDto();
        dto.setUsuario("gabriel");
        dto.setSenha("123456");

        when(usuarioRepository.findByUsuario(dto.getUsuario())).thenReturn(new Usuario());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });

        assertEquals("Usuário já existente", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveListarUsuarios() {
        when(usuarioRepository.findAllUsuarios()).thenReturn(Arrays.asList("gabriel", "ana"));

        List<String> resultado = usuarioService.listarUsuarios();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains("gabriel"));
        verify(usuarioRepository, times(1)).findAllUsuarios();
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setUsuario("gabriel");

        when(usuarioRepository.findById(id)).thenReturn(java.util.Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        String resultado = usuarioService.atualizarUsuario(usuario.getUsuario(), id);

        assertEquals("Usuário alterado com sucesso", resultado);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void atualizarUsuarioUsuarioNaoEncontrado() {
        Long id = 1L;
        UsuarioDto dto = new UsuarioDto();
        dto.setUsuario("gabriel");

        when(usuarioRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> usuarioService.atualizarUsuario(dto.getUsuario(), id));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveAtualizarSenhaComSucesso() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setUsuario("gabriel");
        usuario.setSenha("senhaHashAntiga");

        SenhaDto senhaDto = new SenhaDto();
        senhaDto.setSenhaAtual("123456");
        senhaDto.setNovaSenha("654321");

        when(usuarioRepository.findById(id)).thenReturn(java.util.Optional.of(usuario));
        when(bCryptPasswordEncoder.matches("123456", "senhaHashAntiga")).thenReturn(true);
        when(bCryptPasswordEncoder.encode("654321")).thenReturn("senhaHashNova");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        String resultado = usuarioService.atualizarSenha(senhaDto, id);

        assertEquals("Senha alterada com sucesso", resultado);
        assertEquals("senhaHashNova", usuario.getSenha());
    }

    @Test
    void atualizarSenhaSenhaIncorreta() {
    	Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setUsuario("gabriel");
        usuario.setSenha("senhaHashAntiga");

        SenhaDto senhaDto = new SenhaDto();
        senhaDto.setSenhaAtual("errada");
        senhaDto.setNovaSenha("654321");

        when(usuarioRepository.findById(id)).thenReturn(java.util.Optional.of(usuario));
        when(bCryptPasswordEncoder.matches("errada", "senhaHashAntiga")).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> usuarioService.atualizarSenha(senhaDto, id));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deletarUsuarioComSucesso() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setUsuario("gabriel");

        when(usuarioRepository.findById(id)).thenReturn(java.util.Optional.of(usuario));
        doNothing().when(usuarioRepository).deleteById(id);

        String resultado = usuarioService.deletarUsuario(id);

        assertEquals("Usuário deletado com sucesso", resultado);
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    void deletarUsuarioNaoEncontrado() {
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> usuarioService.deletarUsuario(id));
        verify(usuarioRepository, never()).deleteById(id);
    }
}