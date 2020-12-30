package br.ufsm.csi.biblioteca.repository;

import br.ufsm.csi.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    List<Livro> findAll();
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByEditora(String editora);
    Optional<Livro> findByIsbn(String isbn);
}
