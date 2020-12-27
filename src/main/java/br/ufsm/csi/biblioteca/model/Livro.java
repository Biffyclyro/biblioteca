package br.ufsm.csi.biblioteca.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

@Data
@Entity
@NoArgsConstructor
public class Livro {

    public Livro(String titulo, String autores, String isbn, String editora, String edicao, String ano) {
        this.titulo = titulo;
        this.autores = autores;
        this.isbn = isbn;
        this.editora = editora;
        this.ano = ano;
    }

    @Id
    @Column(name = "id_livro")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idLivro;
    private String titulo;
    private String autores;
    @Column(unique = true)
    private String isbn;
    private String edicao;
    private String editora;
    private String ano;
    private boolean emprestado;
    @OneToMany
    @OrderBy("idUsuario")
    private List<Usuario> reservas = new ArrayList<Usuario>();

    public Queue<Usuario> getReservas() {
        return new LinkedList<Usuario>(this.reservas);
    }

    public void reservar(Usuario u){
        this.reservas.add(u);
    }
}
