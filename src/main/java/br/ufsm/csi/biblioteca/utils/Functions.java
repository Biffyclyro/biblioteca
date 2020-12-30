package br.ufsm.csi.biblioteca.utils;

import br.ufsm.csi.biblioteca.model.Usuario;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

public class Functions {

    public static LocalDate dataGlobal = LocalDate.now();

    public static boolean checkSession(HttpSession s) {
        return s.getAttribute("usuario") instanceof Usuario;
    }

    public static void adiantaData(){
        dataGlobal = dataGlobal.plusDays(1);
    }
}
