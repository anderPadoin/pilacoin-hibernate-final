package br.ufsm.csi.seguranca.pila.model;

import br.ufsm.csi.seguranca.util.RSAUtil;
import br.ufsm.csi.seguranca.util.Utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EnviaMensagemDatagram implements Runnable {

    private DatagramSocket socketCliente = null;
    private String idCliente = "id-anderson";
    private boolean minerar = false;
    public EnviaMensagemDatagram(DatagramSocket socket) {
        this.socketCliente = socket;
        //this.idCliente = id;
    }

    @Override
    public void run() {
        while (true) {

            try {
                socketCliente.setBroadcast(true);
                while (true) {
                    Mensagem mensagem = new Mensagem();
                    mensagem.setTipo(Mensagem.TipoMensagem.DISCOVER);
                    mensagem.setIdOrigem(idCliente);
                    mensagem.setPorta(socketCliente.getLocalPort());
                    mensagem.setMaster(false);
                    mensagem.setAssinatura(null);
                    mensagem.setChavePublica(RSAUtil.getPublicKey("chave_publica"));

                    byte[] msgSerial = Utils.serializa(mensagem);
                    DatagramPacket datagramPacket = new DatagramPacket(msgSerial, msgSerial.length, InetAddress.getByName("192.168.90.194"), 3333);
                    socketCliente.send(datagramPacket);
                    System.out.println("Enviado mensagem DISCOVER");
                    datagramPacket = new DatagramPacket(msgSerial, msgSerial.length, InetAddress.getByName("255.255.255.255"), 3333);
                    socketCliente.send(datagramPacket);
                    Thread.sleep(15000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
