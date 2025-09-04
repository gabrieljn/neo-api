package com.neo.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.neo.domain.Usuario;
import com.neo.exception.InvalidPasswordException;
import com.neo.repository.UsuarioRepository;
import com.neo.service.impl.LoginServiceImpl;

public class LoginServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private LoginServiceImpl loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginComSucesso() {
        String usuarioLogin = "gabriel";
        String senha = "123456";

        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioLogin);
        usuario.setSenha("senhaHash");

        when(usuarioRepository.findByUsuario(usuarioLogin)).thenReturn(usuario);
        when(bCryptPasswordEncoder.matches(senha, "senhaHash")).thenReturn(true);

        assertDoesNotThrow(() -> loginService.login(usuarioLogin, senha));
    }

    @Test
    void loginUsuarioNaoEncontrado() {
        String usuarioLogin = "gabriel";
        String senha = "123456";

        when(usuarioRepository.findByUsuario(usuarioLogin)).thenReturn(null);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            loginService.login(usuarioLogin, senha);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void loginSenhaIncorreta() {
        String usuarioLogin = "gabriel";
        String senha = "123456";

        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioLogin);
        usuario.setSenha("senhaHash");

        when(usuarioRepository.findByUsuario(usuarioLogin)).thenReturn(usuario);
        when(bCryptPasswordEncoder.matches(senha, "senhaHash")).thenReturn(false);

        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> {
            loginService.login(usuarioLogin, senha);
        });

        assertEquals("Senha informada está incorreta", exception.getMessage());
    }
}