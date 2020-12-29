package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Livro;
import br.ufsm.csi.biblioteca.model.Usuario;
import br.ufsm.csi.biblioteca.repository.LivroRepository;
import br.ufsm.csi.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    public String listarLivros( HttpSession session ,Model model) {
        if (!this.checkSession(session)){

            return "redirect:/usuario/login";
        }
        final var acervo = livroRepository.findAll();
        model.addAttribute("acervo", acervo);



        return "livros";
    }

    @GetMapping("emprestar")
    public String emprestaLivros( HttpSession session,@RequestParam int idLivro, @RequestParam int idUsuario) {
        if (!this.checkSession(session)){

            return "redirect:/usuario/login";
        }

        final var optLivro = livroRepository.findById(idLivro);


        if (optLivro.isPresent()){
            final var livro = optLivro.get();
            final var usuario = (Usuario) session.getAttribute("usuario");
            usuario.pegarLivro(livro);
            livroRepository.save(livro);
        }

        return "redirect:/livros";
    }

    @GetMapping("reservar")
    public String reservar( HttpSession session,@RequestParam int idLivro, @RequestParam int id_usuario){
        if (!this.checkSession(session)){

            return "redirect:/usuario/login";
        }

        final var optLivro = livroRepository.findById(idLivro);
        final var optUsuario = usuarioRepository.findById(id_usuario);

        if (optLivro.isPresent() && optUsuario.isPresent()){
            final var livro = optLivro.get();
            final var usuario = optUsuario.get();

            livro.reservar(usuario);

            livroRepository.save(livro);

        }

        return "redirect:/listar";
    }
     @GetMapping("reservar/cancelar/{id_livro}")
     public String cancelarReserva(HttpSession session) {
         if (!this.checkSession(session)){

             return "redirect:/usuario/login";
         }
        return "redirect:/listar";
     }

    @GetMapping("/devolver/{id_livro}")
    public String devolver( HttpSession session,@PathVariable int idLivro){
        if (!this.checkSession(session)){

            return "redirect:/usuario/login";
        }
        return "redirect:listar";

    }

    @GetMapping("/cadastro")
    public String paginaCadastro(HttpSession session) {
        if (!this.checkSession(session)){

            return "redirect:/usuario/login";
        }
        return "cadastro_livro";
    }

    @PostMapping("/cadastrar")
    public String cadastrarLivro(HttpSession session ,Livro livro) {
        if (!this.checkSession(session)){

            return "redirect:/usuario/login";
        }
        livro.setEmprestado(false);
        this.livroRepository.save(livro);
        return "redirect:/livros";
    }

    private boolean checkSession(HttpSession s) {
        return s.getAttribute("usuario") instanceof Usuario;
    }

}
