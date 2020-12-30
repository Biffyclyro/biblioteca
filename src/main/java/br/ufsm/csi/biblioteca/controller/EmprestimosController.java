package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Livro;
import br.ufsm.csi.biblioteca.repository.LivroRepository;
import br.ufsm.csi.biblioteca.repository.UsuarioRepository;
import br.ufsm.csi.biblioteca.utils.Functions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.ufsm.csi.biblioteca.utils.Functions.checkSession;

@Controller
@RequestMapping("emprestimos")
public class EmprestimosController {
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimosController(UsuarioRepository usuarioRepository, LivroRepository livroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public String listarEmprestimos(HttpSession session, Model model) {
        if (!checkSession(session)) {
            return "redirect:/usuario/login";
        }
        final List<Livro> livros_emprestados = new ArrayList<>();
        final var livros = livroRepository.findAll();

        livros.forEach(livro -> {
            System.out.println(livro.getTitulo());
            if (livro.isEmprestado()) livros_emprestados.add(livro);
        });

        model.addAttribute("livros", livros_emprestados);

        return "listar_emprestimos";
    }

    @GetMapping("buscar")
    public String buscarEmprestimo(HttpSession session, @RequestParam String texto, Model model) {
        if (!checkSession(session)) {
            return "redirect:/usuario/login";
        }
        final Set<Livro> livroSet = new HashSet<>();
        final var busca1 = livroRepository.findByTituloContainingIgnoreCase(texto);
        final var busca2 = livroRepository.findByIsbn(texto);
        livroSet.addAll(busca1);
        busca2.ifPresent(livroSet:: add);

        model.addAttribute("livros", livroSet.toArray());

        return "listar_emprestimos";
    }

    @PostMapping("listar_de_usuario")
    public String listarDeUsuario(HttpSession session, Model model, @RequestParam int idUsuario){
        if (!checkSession(session)) {
            return "redirect:/usuario/login";
        }
        final var livros = livroRepository.findAll();
        final List<Livro> livros_emprestados = new ArrayList<>();

        livros.forEach(livro -> {
            if (livro.getEmprestadoPara().getIdUsuario() == idUsuario) livros_emprestados.add(livro);
        });

        model.addAttribute("livros", livros_emprestados);

        return "listar_emprestimos";

    }



}
