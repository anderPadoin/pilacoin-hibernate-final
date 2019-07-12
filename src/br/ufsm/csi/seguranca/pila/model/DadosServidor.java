package br.ufsm.csi.seguranca.pila.model;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DadosServidor {

    private static int porta = 0;
    private static InetAddress endereco;
    private static Set<String> usuarios = new HashSet<>();

    public static int getPorta() {
        return porta;
    }

    public static void setPorta(int porta) {
        DadosServidor.porta = porta;
    }

    public static InetAddress getEndereco() {
        return endereco;
    }

    public static void setEndereco(InetAddress endereco) {
        DadosServidor.endereco = endereco;
    }

    public static Set<String> getUsuarios() {
        return usuarios;
    }

    public static void setUsuarios(Set<String> usuarios) {
        DadosServidor.usuarios = usuarios;
    }
}
