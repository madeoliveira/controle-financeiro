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

import github.madeoliveira.controlefinanceiro.exceptions.ErroAutenticacao;
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
		String email = "usuario@email.com";
		String senha = "senha";
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

		Usuario result = service.autenticar(email, senha);
		Assertions.assertThat(result).isNotNull();
	}

	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComEmailInformado() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("usuario@email.com", "senha"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado");
	}

	@Test
	public void deveLancarErroQuandoSenhaNãoCorresponder() {
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("usuario@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("usuario@email.com", "123"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida!");
	}

	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		service.validarEmail("usuario@email.com");
	}

	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		service.validarEmail("usuario@email.com");

	}
}
