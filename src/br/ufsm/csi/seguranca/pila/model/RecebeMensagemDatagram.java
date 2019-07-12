package br.ufsm.csi.seguranca.pila.model;

import br.ufsm.csi.seguranca.util.RSAUtil;
import br.ufsm.csi.seguranca.util.Utils;

import javax.crypto.Cipher;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class RecebeMensagemDatagram implements Runnable {

    private DatagramSocket socketCliente = null;
    private String id = "id-anderson";

    public RecebeMensagemDatagram(DatagramSocket socket) {
        this.socketCliente = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] respostaServer = new byte[1500];
                DatagramPacket pacoteServidor = new DatagramPacket(respostaServer, respostaServer.length);
                socketCliente.receive(pacoteServidor);
                Mensagem mensagemServidor = (Mensagem) Utils.deserializa(pacoteServidor.getData());
                if (mensagemServidor.getTipo() == Mensagem.TipoMensagem.DISCOVER_RESP) {

                    System.out.println("Mensagem DISCOVER_RESP recebida");
                    Cipher cipher = Cipher.getInstance("RSA");
                    cipher.init(Cipher.DECRYPT_MODE, RSAUtil.getMasterPublicKey());
                    byte[] assinaturaServidor = cipher.doFinal(mensagemServidor.getAssinatura());

                    mensagemServidor.setAssinatura(null);
                    byte[] assinaturaLocal = Utils.hashObject(Utils.serializa(mensagemServidor), "SHA-256");

                    if (Arrays.equals(assinaturaLocal, assinaturaServidor)) {
                        if (mensagemServidor.isMaster()) {
                            System.out.println("Achou servidor: "+ mensagemServidor.getEndereco().getHostName() + ":" + mensagemServidor.getPorta());
                            DadosServidor.setPorta(mensagemServidor.getPorta());
                            DadosServidor.setEndereco(mensagemServidor.getEndereco());
                            new Thread(new Minerador()).start();
                        }else {
                            DadosServidor.setPorta(0);
                            DadosServidor.setEndereco(null);
                            System.out.println("Não é master");
                        }
                    }else {
                        System.out.println("Assinatura incorreta!");
                    }

                } else if (mensagemServidor.getTipo() == Mensagem.TipoMensagem.DISCOVER) {
                    //if (!mensagemServidor.getIdOrigem().equals(id)) {
                        System.out.println("Recebeu id: " + mensagemServidor.getIdOrigem());
                        DadosServidor.getUsuarios().add(mensagemServidor.getIdOrigem());
                        System.out.println(DadosServidor.getUsuarios().size());
                    //}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
