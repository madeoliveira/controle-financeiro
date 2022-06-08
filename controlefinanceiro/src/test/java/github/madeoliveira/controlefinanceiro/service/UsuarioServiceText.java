package github.madeoliveira.controlefinanceiro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import github.madeoliveira.controlefinanceiro.exceptions.RegraNegocioException;
import github.madeoliveira.controlefinanceiro.model.entity.Usuario;
import github.madeoliveira.controlefinanceiro.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceText {

	@Autowired
	UsuarioService service;
	@Autowired
	UsuarioRepository repository;

	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		repository.deleteAll();
		service.validarEmail("email@email.com");
	}
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErrroAoValidarEmailQuandoExistirEmailCadastrado () {
		Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
		repository.save(usuario);
		service.validarEmail("email@email.com");
		
	}
}
