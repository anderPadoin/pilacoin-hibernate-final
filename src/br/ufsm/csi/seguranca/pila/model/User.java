package br.ufsm.csi.seguranca.pila.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by cpol on 29/05/2017.@localhost
 */
@Entity
@Table(name = "USER")
public class User {

    private Long id;
    private String nome;
    private String login;
    private byte[] senha;
    private Collection<Log> logs;
    private Boolean admin;

    @Column(name = "ADMIN")
    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Log> getLogs() {
        return logs;
    }

    public void setLogs(Collection<Log> logs) {
        this.logs = logs;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NOME", length = 100)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "LOGIN", length = 100, unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "SENHA", length = 50)
    public byte[] getSenha() {
        return senha;
    }

    public void setSenha(byte[] senha) {
        this.senha = senha;
    }
}
