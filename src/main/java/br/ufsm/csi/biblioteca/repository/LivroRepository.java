package br.ufsm.csi.biblioteca.repository;

import br.ufsm.csi.biblioteca.model.Livro;

import java.util.List;

public interface LivroRepository {
    List<Livro> findAll();
}
