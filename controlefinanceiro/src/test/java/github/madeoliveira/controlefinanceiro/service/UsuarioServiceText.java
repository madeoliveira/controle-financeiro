package github.madeoliveira.controlefinanceiro.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import github.madeoliveira.controlefinanceiro.exceptions.RegraNegocioException;
import github.madeoliveira.controlefinanceiro.model.entity.Usuario;
import github.madeoliveira.controlefinanceiro.model.repository.UsuarioRepository;
import github.madeoliveira.controlefinanceiro.service.impl.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceText {

	UsuarioService service;
	@MockBean
	UsuarioRepository repository;

	@Before
	public void Setup() {
		service = new UsuarioServiceImpl(repository);
	}

	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		String email = "email@email.com";
		String senha = "senha";
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

		Usuario result = service.autenticar(email, senha);
		Assertions.assertThat(result).isNotNull();
	}

	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		service.validarEmail("email@email.com");
	}

	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		service.validarEmail("email@email.com");

	}
}
