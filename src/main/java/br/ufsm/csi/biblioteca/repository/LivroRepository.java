package br.ufsm.csi.biblioteca.repository;

import br.ufsm.csi.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    List<Livro> findAll();
    List<Livro> findAllByTitulo(String titulo);
}
