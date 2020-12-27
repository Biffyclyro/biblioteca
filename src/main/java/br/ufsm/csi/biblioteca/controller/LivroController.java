package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Livro;
import br.ufsm.csi.biblioteca.repository.LivroRepository;
import br.ufsm.csi.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String listarLivros(Model model) {
        final var acervo = livroRepository.findAll();
        model.addAttribute("acervo", acervo);
        return "livros";
    }

    @GetMapping("emprestar")
    public String emprestaLivros(@RequestParam int idLivro) {

        final var optLivro = livroRepository.findById(idLivro);


        if (optLivro.isPresent()){
            final var livro = optLivro.get();
            livro.setEmprestado(true);
            livroRepository.save(livro);
        }

        return "redirect:/livros";
    }

    @GetMapping("reservar")
    public String reservar(@RequestParam int idLivro, @RequestParam int id_usuario){

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
     public String cancelarReserva() {
        return "redirect:/listar";
     }

    @GetMapping("/devolver/{id_livro}")
    public String devolver(@PathVariable int idLivro){
        return "redirect:listar";

    }

    @GetMapping("/cadastro")
    public String paginaCadastro() {
        return "cadastro_livro";
    }

    @PostMapping("/cadastrar")
    public String cadastrarLivro(Livro livro) {
        livro.setEmprestado(false);
        this.livroRepository.save(livro);
        return "redirect:/livros";
    }


}
