package br.com.modelo.pontointeligente.api.security.repositories;

import br.com.modelo.pontointeligente.api.security.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Usuario findByEmail(String email);
	Usuario findByIdEquals(Long usuarioId);
}
