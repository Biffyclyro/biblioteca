package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Livro;
import br.ufsm.csi.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroController {
    private final LivroRepository livroRepository;

    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public String listarLivros(Model model) {
        final var acervo = livroRepository.findAll();
        model.addAttribute("acervo", acervo);
        return "livros";
    }

    @GetMapping("/emprestar/{id_livro}")
    public String emprestaLivros(@PathVariable int idLivro) {
        return "redirect:/listar";
    }

    @GetMapping("/reservar/{id_livro}")
    public String reservar(@PathVariable int idLivro){
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

    @PostMapping("/cadastrar")
    public String cadastrarLivro(@RequestParam Livro livro) {
        return "redirect:/listar";
    }


}
