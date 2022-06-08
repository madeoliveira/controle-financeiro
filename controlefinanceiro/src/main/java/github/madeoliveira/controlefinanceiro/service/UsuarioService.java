package github.madeoliveira.controlefinanceiro.service;

import github.madeoliveira.controlefinanceiro.model.entity.Usuario;

public interface UsuarioService {
	Usuario autenticar( String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
}
