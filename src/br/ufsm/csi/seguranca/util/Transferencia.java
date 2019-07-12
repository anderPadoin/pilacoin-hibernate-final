package br.ufsm.csi.seguranca.util;

import br.ufsm.csi.seguranca.pila.model.PilaCoin;
import br.ufsm.csi.seguranca.pila.model.Transacao;
import br.ufsm.csi.seguranca.pila.model.Usuario;
import br.ufsm.csi.seguranca.pilacoin.PilaDHTClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
/*
public class Transferencia {

    public static void main(String[] args) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setChavePublica(RSAUtil.getPublicKey("chave_publica"));
        usuario.setId("id-anderson");

        PilaDHTClient pilaDHTClient = new PilaDHTClient("192.168.90.194", 4001, usuario);
        try {
            while (true) {
                System.out.println("Que operação deseja realizar? \n1 - Transferir pilacoin\n2 - Receber Pila");
                Scanner scanner = new Scanner(System.in);
                String valor = scanner.nextLine();
                if (valor.equals("1")) {

                    Usuario meuUsuario = pilaDHTClient.getUsuario("id-anderson");
                    System.out.println("Você possui " + meuUsuario.getMeusPilas().size() + " pilas");
                    System.out.println("Meus pilas:");

                    for (PilaCoin pilaCoin : meuUsuario.getMeusPilas()) {
                        System.out.print("[" + pilaCoin.getId() + "]");
                    }

                    System.out.println("\nInforme o  ID do pila que deseja transferir");
                    String idPila;
                    idPila = scanner.nextLine();
                    //PilaCoin pilaSel = getPilaSet(idPila, meuUsuario.getMeusPilas());
                    while (pilaSel == null) {
                        System.out.println("Pila invalido, informe outro valor:");
                        //pilaSel = getPilaSet(scanner.nextLine(), meuUsuario.getMeusPilas());
                    }
                    System.out.println("Informe o id do usuário destino");
                    String idDestino = scanner.nextLine();
                    Usuario usuarioDestino = pilaDHTClient.getUsuario(idDestino);
                    if (usuarioDestino == null) {
                        System.out.println("Usuário não encontrado");
                    } else {
                        pilaDHTClient.setPilaCoin(novaTransacao(pilaSel, usuarioDestino));
                        System.out.println("Usuário encontrado. Iniciando transferencia");

                        System.out.println("\n");
                    }

                } else {
                    System.out.println("Opção inválida");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static PilaCoin getPilaSet(long id, Set<PilaCoin> pilas) {
        System.out.println();
        for (PilaCoin pila : pilas) {
            if (pila.getId().toString().equals(id)) {
                return pila;
            }
        }
        return null;
    }

    public static PilaCoin novaTransacao(PilaCoin pilaCoin, Usuario usuarioDestino) throws Exception {
        Transacao transacao = new Transacao();
        transacao.setIdNovoDono(usuarioDestino.getId());
        transacao.setDataTransacao(new Date());
        transacao.setChaveNovoDono(usuarioDestino.getChavePublica());

        byte[] transacaoSerializada = Utils.serializa(transacao);
        byte[] hashTransacao = Utils.hashObject(transacaoSerializada, "SHA-256");

        transacao.setAssinaturaDono(Utils.criptografar("RSA", RSAUtil.getPrivateKey("chave_privada"), hashTransacao));
        ArrayList<Transacao> transacoes = new ArrayList();
        transacoes.add(transacao);
        pilaCoin.setTransacoes(transacoes);
        return pilaCoin;
    }
}*/
