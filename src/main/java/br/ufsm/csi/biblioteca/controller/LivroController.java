package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Livro;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroController {

    @GetMapping
    public String listarLivros() {
        return "listar";
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
