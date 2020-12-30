package br.ufsm.csi.biblioteca.controller;

import br.ufsm.csi.biblioteca.model.Usuario;
import br.ufsm.csi.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Set;

import static br.ufsm.csi.biblioteca.utils.Functions.checkSession;

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

    @GetMapping("listar")
    public String listarUsuarios(HttpSession session, Model model) {
        if (!checkSession(session)) {
           return "redirect:login";
        }
        final var usuario = (Usuario) session.getAttribute("usuario");
        if (usuario.getTipoUsuario() == Usuario.TipoUsuario.SUPER) {
            final var usuarios = usuarioRepository.findAll();
            model.addAttribute("usuarios", usuarios);

            return "usuarios_listar";
        }

        return "redirect:/livros";
    }

    @GetMapping("buscar")
    public String buscarUsuario(HttpSession session, @RequestParam String texto, Model model) {
        Set<Usuario> usuarioSet = new HashSet<>();
        final var busca1 = usuarioRepository.findByNomeContainingIgnoreCase(texto);
        final var busca2 = usuarioRepository.findByEmail(texto);

        usuarioSet.addAll(busca1);
        busca2.ifPresent(usuarioSet :: add);

        model.addAttribute("usuarios", usuarioSet.toArray());

        return "usuarios_listar";
    }

    @GetMapping("/editar")
    public String editar(HttpSession session, Model model) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        final var usuario = (Usuario) session.getAttribute("usuario");

        model.addAttribute("usuario", usuario);

        return "editar_usuario";
    }

    @PostMapping("/editar")
    public String editarUsuario(HttpSession session, Usuario usuario) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        final var usuarioSession = (Usuario) session.getAttribute("usuario");

        usuario.setIdUsuario(usuarioSession.getIdUsuario());
        usuario.setTipoUsuario(usuarioSession.getTipoUsuario());

        usuarioRepository.save(usuario);

        return "redirect:/logout";
    }

    @DeleteMapping
    public String deletarUsuario(HttpSession session, @RequestParam int idUsuario) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }
        final var usuario = (Usuario) session.getAttribute("usuario");

        if (usuario.getIdUsuario() != idUsuario && usuario.getTipoUsuario() == Usuario.TipoUsuario.SUPER){
            usuarioRepository.deleteById(idUsuario);
             return "redirect:listar";
        }

        usuarioRepository.deleteById(usuario.getIdUsuario());

        return "redirect:/logout";
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
