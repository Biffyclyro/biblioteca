package br.ufsm.csi.biblioteca.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario")
    private int idUsuario;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    @Column(name = "tipo_usuario", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
    private int LIMITE_LIVROS;
    private int emprestados = 0;
    private double multa = 0;

    public enum TipoUsuario {
        SUPER,
        PROFESSOR,
        ALUNO
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email= email;
        this.senha = senha;

    }


    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        if (tipoUsuario == TipoUsuario.PROFESSOR) {
            setLIMITE_LIVROS(5);
        } else {
            setLIMITE_LIVROS(3);
        }
        this.tipoUsuario = tipoUsuario;
    }

    public void pegarLivro(Livro l) {
        if (this.emprestados < this.LIMITE_LIVROS) {
            l.emprestar(this);
            this.emprestados++;
        }
    }

    public void devolver(Livro l) {
        l.devolver(this);
        this.emprestados--;
    }

    public double pagarMulta(double dinheiro) {
        if (this.multa - dinheiro <= 0 )     {
            this.multa -= dinheiro;
            return 0;
        } else {
            this.multa = 0;
            return dinheiro - this.multa;
        }
    }

}
