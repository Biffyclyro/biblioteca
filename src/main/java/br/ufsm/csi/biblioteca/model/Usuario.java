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
    private BigInteger idUsuario;
    private String nome;
    @Column(unique = true)
    private String login;
    private String senha;
    @Column(name = "tipo_usuario", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
    private int LIMITE_LIVROS;

    public enum TipoUsuario {
        PROFESSOR,
        ALUNO
    }

    public Usuario(String nome, String login, String senha, TipoUsuario tipoUsuario, int limite_livros) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        if( tipoUsuario == TipoUsuario.PROFESSOR) {
            this.LIMITE_LIVROS = 5;
        } else {
            this.LIMITE_LIVROS = 3;
        }
    }
}
