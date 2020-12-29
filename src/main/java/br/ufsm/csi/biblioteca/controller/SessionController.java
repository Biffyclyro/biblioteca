package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Usuario;
import br.ufsm.csi.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("usuario")
public class SessionController {
    private final UsuarioRepository usuarioRepository;

    public SessionController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute("usuario")
    public Usuario returnUsuarioSession() {
        return new Usuario();
    }

    @GetMapping("/session/{idUsuario}")
    public String salvaSession(@ModelAttribute("usuario") Usuario u, @PathVariable int idUsuario){
        final var optUsuario = usuarioRepository.findById(idUsuario);

        if (optUsuario.isPresent()){
            final var usuario = optUsuario.get();
            u.setIdUsuario(usuario.getIdUsuario());
            u.setLIMITE_LIVROS(usuario.getLIMITE_LIVROS());
            u.setNome(usuario.getNome());
            u.setTipoUsuario(usuario.getTipoUsuario());
            u.setMulta(usuario.getMulta());

        }

        return "redirect:/livros";
    }

}
