package br.ufsm.csi.seguranca.pila.service;

import br.ufsm.csi.seguranca.pila.model.DadosServidor;
import br.ufsm.csi.seguranca.pila.model.EnviaMensagemDatagram;
import br.ufsm.csi.seguranca.pila.model.Minerador;
import br.ufsm.csi.seguranca.pila.model.RecebeMensagemDatagram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.DatagramSocket;
import java.net.SocketException;

@Repository
public class MineracaoService {

    private boolean status = false;

    @Autowired
    Minerador minerador;

    DatagramSocket socket;

    public MineracaoService() throws SocketException {
        socket = new DatagramSocket(3333);
        new Thread(new EnviaMensagemDatagram(socket)).start();
        new Thread(new RecebeMensagemDatagram(socket)).start();
    }


    public boolean isStatus() {
        return status;
    }

    public synchronized boolean switchMineracao() throws SocketException {
        boolean serverFound = false;

        if (DadosServidor.getUsuarios() != null && DadosServidor.getPorta() != 0) {
            serverFound = true;
            if (!status) {
                minerador.iniciaMineracao();
                status = !status;
            } else {
                status = !status;
                minerador.terminaMineracao();

            }
        } else {
            serverFound = false;
            if (status) {
                status = !status;
                minerador.terminaMineracao();
            }
        }
        return serverFound;
    }

}
