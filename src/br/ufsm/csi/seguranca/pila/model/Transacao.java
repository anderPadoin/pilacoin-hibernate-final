package br.ufsm.csi.seguranca.pila.model;

import javax.persistence.*;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.Date;

/**
 * Created by cpol on 17/04/2018.
 */
//@Entity
//@Table(name = "TRANSACAO")
public class Transacao implements Serializable {

    private static final long serialVersionUID = 2L;

    private String idNovoDono;
    private PublicKey chaveNovoDono;
    private Date dataTransacao;
    private byte[] assinaturaDono;

        //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "ID_PILACOIN")
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    //@Column(name = "CHAVE_NOVO_DONO")
    public PublicKey getChaveNovoDono() {
        return chaveNovoDono;
    }

    public void setChaveNovoDono(PublicKey chaveNovoDono) {
        this.chaveNovoDono = chaveNovoDono;
    }

    //@Column(name = "ID_NOVO_DONO")
    public String getIdNovoDono() {
        return idNovoDono;
    }

    public void setIdNovoDono(String idNovoDono) {
        this.idNovoDono = idNovoDono;
    }

    //@Column(name = "DATA_TRANSACAO")
    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    //@Column(name = "ASSINATURA_DONO")
    public byte[] getAssinaturaDono() {
        return assinaturaDono;
    }

    public void setAssinaturaDono(byte[] assinaturaDono) {
        this.assinaturaDono = assinaturaDono;
    }
}
