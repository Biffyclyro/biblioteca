package br.ufsm.csi.biblioteca.repository;

import br.ufsm.csi.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
