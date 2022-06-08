package github.madeoliveira.controlefinanceiro.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import github.madeoliveira.controlefinanceiro.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	boolean existsByEmail(String email);
}
