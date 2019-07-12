package br.ufsm.csi.seguranca.pila.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cpol on 05/06/2017.
 */
@Entity
@Table(name = "LOG")
public class Log {

    private Long id;
    private Class classe;
    private Long idObjeto;
    private User user;
    private Date dataHora;
    private String tipo;

    @Column(name = "TIPO")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_log")
    @SequenceGenerator(name = "seq_log", sequenceName = "seq_log")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CLASSE")
    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    @Column(name = "ID_OBJETO")
    public Long getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Long idObjeto) {
        this.idObjeto = idObjeto;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "DATA_HORA")
    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}
