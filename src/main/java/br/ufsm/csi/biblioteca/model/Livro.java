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
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario emprestadoPara;
    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("idUsuario")
    private List<Usuario> reservas = new ArrayList<Usuario>();

    public Livro(String titulo, String autores, String isbn, String editora, String edicao, String ano) {
        this.titulo = titulo;
        this.autores = autores;
        this.isbn = isbn;
        this.editora = editora;
        this.ano = ano;
    }

    public void emprestar(Usuario u){
        setEmprestado(true);
        setEmprestadoPara(u);
    }

    public void devolver(Usuario u) {
        if (u.getIdUsuario() == this.emprestadoPara.getIdUsuario()) {
            this .emprestadoPara = null;
            this.setEmprestado(false);
        }
    }


    public Queue<Usuario> getReservas() {
        //this.reservas.forEach(x -> System.out.println(x.getEmail()));
        return new LinkedList<Usuario>(this.reservas);
    }



    public void reservar(Usuario u){
        this.reservas.add(u);
    }

    public void cancelarReserva(Usuario u) {
        this.reservas.remove(u);
    }
}
