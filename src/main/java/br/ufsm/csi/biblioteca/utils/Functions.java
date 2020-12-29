package br.ufsm.csi.biblioteca.utils;

import br.ufsm.csi.biblioteca.model.Usuario;

import javax.servlet.http.HttpSession;

public class Functions {
    public static boolean checkSession(HttpSession s) {
        return s.getAttribute("usuario") instanceof Usuario;
    }
}
