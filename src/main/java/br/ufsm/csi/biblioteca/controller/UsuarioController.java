package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Usuario;
import br.ufsm.csi.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("login")
    public String retornaLogin() {
        return "login";
    }

    @PostMapping("login")
    public String login(@RequestParam String email, @RequestParam String senha) {

        final var optUsuario = usuarioRepository.findByEmail(email);
        if(optUsuario.isPresent()){

            final var u = optUsuario.get();

            System.out.println( u.getSenha());
            if(u.getSenha().equals(senha)){

                return "redirect:/session/" + u.getIdUsuario();
            }
        }

        return "redirect:login";

    }

    @GetMapping("/cadastro")
    public String retornaCadastro() {
        return "cadastro_usuario";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Usuario usuario, @RequestParam(defaultValue = "false") boolean professor) {

        if (professor) {
            usuario.setTipoUsuario(Usuario.TipoUsuario.PROFESSOR);
        } else {
            usuario.setTipoUsuario(Usuario.TipoUsuario.ALUNO);
        }

        if (usuario.getNome().equals("SUPER")) {
            usuario.setTipoUsuario(Usuario.TipoUsuario.SUPER);
        }

        usuarioRepository.save(usuario);


       return "redirect:/usuario/login";
    }

    @PostMapping("/pagar")
    public String pagar(@RequestParam double multa) {
        return "redirect:/home";
    }

   @GetMapping("/logout")
    public String deslogar(HttpSession session) {
        session.invalidate();

        return "redirect:/";
   }
}
