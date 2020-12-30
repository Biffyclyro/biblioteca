package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Livro;
import br.ufsm.csi.biblioteca.model.Usuario;
import br.ufsm.csi.biblioteca.repository.LivroRepository;
import br.ufsm.csi.biblioteca.repository.UsuarioRepository;
import br.ufsm.csi.biblioteca.utils.Functions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static br.ufsm.csi.biblioteca.utils.Functions.checkSession;

@Controller
public class EventosTemporaisController {
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EventosTemporaisController(LivroRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("adianta_dia")
    public String adiantaDia(HttpSession session){
        if (!checkSession(session)) {
            return "redirect:/usuario/login";
        }
        Functions.adiantaData();
        final var livros = livroRepository.findAll();
        final var usuarioLogado = (Usuario) session.getAttribute("usuario");

        livros.forEach(livro -> {
            usuarioRepository.save(livro.multar());
        });



        return "redirect:/session/" + usuarioLogado.getIdUsuario();

    }

}
