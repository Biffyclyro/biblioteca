package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Livro;
import br.ufsm.csi.biblioteca.model.Usuario;
import br.ufsm.csi.biblioteca.repository.LivroRepository;
import br.ufsm.csi.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static br.ufsm.csi.biblioteca.utils.Functions.checkSession;

@Controller
@RequestMapping("livros")
public class LivroController {
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public LivroController(LivroRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarLivros(HttpSession session, Model model) {
        if (!checkSession(session)) {
            return "redirect:/usuario/login";
        }

        final var acervo = livroRepository.findAll();
        acervo.forEach(x -> {
            System.out.println(x.getReservas().contains(session.getAttribute("usuario")));
        });
        model.addAttribute("acervo", acervo);

        return "livros";
    }

    @GetMapping("emprestar")
    public String emprestaLivros(HttpSession session, @RequestParam int idLivro, @RequestParam int idUsuario) {
        if (!checkSession(session)) {

            return "redirect:/usuario/login";
        }

        final var optLivro = livroRepository.findById(idLivro);
        final var usuarioLogado = (Usuario) session.getAttribute("usuario");
        final var optUsuario = usuarioRepository.findById(usuarioLogado.getIdUsuario());

        if (optLivro.isPresent() && optUsuario.isPresent()) {
            final var livro = optLivro.get();
            final var usuario = optUsuario.get();
            usuario.pegarLivro(livro);
            usuarioRepository.save(usuario);
            livroRepository.save(livro);
        }

        return "redirect:/session/" + idUsuario;
    }
    @GetMapping("renovar")
    public String renovarLivros(HttpSession session, @RequestParam int idLivro, @RequestParam int idUsuario) {
        if (!checkSession(session)) {

            return "redirect:/usuario/login";
        }

        final var optLivro = livroRepository.findById(idLivro);
        final var usuarioLogado = (Usuario) session.getAttribute("usuario");
        final var optUsuario = usuarioRepository.findById(usuarioLogado.getIdUsuario());

        if (optLivro.isPresent() && optUsuario.isPresent()) {
            final var livro = optLivro.get();
            final var usuario = optUsuario.get();
            usuario.pegarLivro(livro);
            usuarioRepository.save(usuario);
            livroRepository.save(livro);
        }
        return "redirect:/session/" + idUsuario;
    }

    @GetMapping("reservar")
    public String reservar(HttpSession session, @RequestParam int idLivro) {
        if (!checkSession(session)) {

            return "redirect:/usuario/login";
        }

        final var optLivro = livroRepository.findById(idLivro);
        final var usuarioLogado = (Usuario) session.getAttribute("usuario");
        final var optUsuario = usuarioRepository.findById(usuarioLogado.getIdUsuario());

        if (optLivro.isPresent() && optUsuario.isPresent()) {
            final var livro = optLivro.get();
            final var usuario = optUsuario.get();

            livro.reservar(usuario);
            usuarioRepository.save(usuario);
            livroRepository.save(livro);

            return "redirect:/session/" + usuario.getIdUsuario();
        }
        return "redirec:/liovros";
    }

    @GetMapping("cancelar")
    public String cancelarReserva(HttpSession session, @RequestParam int idLivro) {
        if (!checkSession(session)) {

            return "redirect:/usuario/login";
        }

        final var optLivro = livroRepository.findById(idLivro);
        final var usuarioLogado = (Usuario) session.getAttribute("usuario");
        final var optUsuario = usuarioRepository.findById(usuarioLogado.getIdUsuario());

        if (optLivro.isPresent() && optUsuario.isPresent()) {
            final var livro = optLivro.get();
            final var usuario = optUsuario.get();


            livro.cancelarReserva(usuario);

            usuarioRepository.save(usuario);
            livroRepository.save(livro);
            return "redirect:/session/" + usuario.getIdUsuario() ;
        }
        return "redirec:/livros";
    }

    @GetMapping("/devolver")
    public String devolver(HttpSession session, @RequestParam int idLivro) {
        if (!checkSession(session)) {

            return "redirect:/usuario/login";
        }

        final var optLivro = livroRepository.findById(idLivro);

        final var usuarioLogado = (Usuario) session.getAttribute("usuario");
        final var optUsuario = usuarioRepository.findById(usuarioLogado.getIdUsuario());
        if (optLivro.isPresent() && optUsuario.isPresent()) {
            final var livro = optLivro.get();
            final var usuario = optUsuario.get();

            usuario.devolver(livro);

            livroRepository.save(livro);
            usuarioRepository.save(usuario);

            return "redirect:/session/" + usuario.getIdUsuario();
        }

        return "redirect:/livros";
    }

    @GetMapping("/cadastro")
    public String paginaCadastro(HttpSession session) {
        if (!checkSession(session)) {

            return "redirect:/usuario/login";
        }
        return "cadastro_livro";
    }

    @PostMapping("/cadastrar")
    public String cadastrarLivro(HttpSession session, Livro livro) {
        if (!checkSession(session)) {

            return "redirect:/usuario/login";
        }
        livro.setEmprestado(false);
        this.livroRepository.save(livro);
        return "redirect:/livros";
    }

    @GetMapping("/editar")
    public String editar(HttpSession session, @RequestParam int idLivro, Model model) {
        if (!checkSession(session)) {
            return "redirect:/usuario/login";
        }

        final var optLivro = livroRepository.findById(idLivro);

        if (optLivro.isPresent()) {
            final var livro = optLivro.get();
            model.addAttribute(livro);
            return "editar_livro";
        }

        return "redirect:/livros";
    }

    @PostMapping("/editar")
    public String editarLivro(HttpSession session, Livro livro) {
        if (!checkSession(session)) {
            return "redirect:/usuario/login";
        }


        livroRepository.save(livro);

        return "redirect:/livros";
    }

    @GetMapping("/buscar")
    public String buscarLivro(HttpSession session, @RequestParam String texto, Model model) {
        if (!checkSession(session)) {
            System.out.println("que?");

            return "redirect:/usuario/login";
        }
        Set<Livro> livroSet = new HashSet<>();

        final var busca1 = livroRepository.findByTituloContainingIgnoreCase(texto);
        final var busca2 = livroRepository.findByIsbn(texto);
        final var busca3 = livroRepository.findByEditora(texto);

        livroSet.addAll(busca1);
        busca2.ifPresent(livroSet::add);
        livroSet.addAll(busca3);

        model.addAttribute("acervo", livroSet.toArray());

        return "livros";
    }

    @PostMapping("deletar")
    public String excluirLivro(@RequestParam int idLivro, HttpSession session) {
        if (!checkSession(session)) {

            return "redirect:/usuario/login";
        }
        final var usuario = (Usuario) session.getAttribute("usuario");

        System.out.println(usuario.getTipoUsuario().name());

        if (usuario.getTipoUsuario() == Usuario.TipoUsuario.SUPER) {
            System.out.println("n entrou");
            livroRepository.deleteById(idLivro);
        }

        return "redirect:/livros";
    }


}
