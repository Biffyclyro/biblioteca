package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @GetMapping
    public String retornaLogin() {
        return "login";
    }

    @PostMapping
    public String login() {
        return "home";
    }

    @GetMapping("/cadastro")
    public String retornaCadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@RequestParam Usuario usuario) {
       return "redirect:/login";
    }

    @PostMapping("/pagar")
    public String pagar(@RequestParam double multa) {
        return "redirect:/home";
    }


}
