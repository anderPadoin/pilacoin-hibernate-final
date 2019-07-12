package br.ufsm.csi.seguranca.pila.controller;

import br.ufsm.csi.seguranca.pila.dao.HibernateDAO;
import br.ufsm.csi.seguranca.pila.model.*;
import br.ufsm.csi.seguranca.pilacoin.PilaDHTClient;
import br.ufsm.csi.seguranca.util.RSAUtil;
import br.ufsm.csi.seguranca.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class PilaController {

    @Autowired
    private HibernateDAO hibernateDAO;

    PilaDHTClient pilaDHTClient;
    Usuario usuario = new Usuario();

    @RequestMapping("lista_meus_pilas.priv")
    public String listaPilasAdmin(Model model, RedirectAttributes redirectAttributes) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setChavePublica(RSAUtil.getPublicKey("chave_publica"));
        usuario.setId("id-anderson");

        pilaDHTClient = new PilaDHTClient("192.168.90.194", 4001, usuario);
        Set<PilaCoin> pilas = pilaDHTClient.getUsuario("id-anderson").getMeusPilas();
        model.addAttribute("pilas", pilas);
        model.addAttribute("usuarios", DadosServidor.getUsuarios());

        return "meus-pilas";
    }
    @Transactional
    @RequestMapping("trans_pila.adm")
    public RedirectView transfPila(RedirectAttributes redirectAttributes, String idDestino, int idPila, ServletRequest request) throws Exception {
        try {
            System.out.println(idPila);
            pilaDHTClient = new PilaDHTClient("192.168.90.194", 4001, usuario);
            Usuario usuarioDestino = pilaDHTClient.getUsuario(idDestino);
            usuario = pilaDHTClient.getUsuario("id-anderson");
            Transacao transacao = new Transacao();
            transacao.setIdNovoDono(usuarioDestino.getId());
            transacao.setDataTransacao(new Date());
            transacao.setChaveNovoDono(usuarioDestino.getChavePublica());
            PilaCoin pilaCoin = getPilaSet(idPila, usuario.getMeusPilas());


            byte[] transacaoSerializada = Utils.serializa(transacao);
            byte[] hashTransacao = Utils.hashObject(transacaoSerializada, "SHA-256");

            transacao.setAssinaturaDono(Utils.criptografar("RSA", RSAUtil.getPrivateKey("chave_privada"), hashTransacao));
            ArrayList<Transacao> transacoes = new ArrayList();
            transacoes.add(transacao);
            pilaCoin.setTransacoes(transacoes);
            pilaDHTClient.setPilaCoin(pilaCoin);
            redirectAttributes.addFlashAttribute("mensagem", "PilaCoin transferido com sucesso para " + idDestino + "!");
            redirectAttributes.addFlashAttribute("tipo", "success");

            Log log = new Log();
            log.setDataHora(new Date());
            log.setClasse(Transacao.class);
            log.setIdObjeto(pilaCoin.getId());
            log.setTipo("transferencia");
            this.geraLog(log);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro na transferencia. Tente novamente");
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }




        return new RedirectView("lista_meus_pilas.priv", true);
    }

    public static PilaCoin getPilaSet(long id, Set<PilaCoin> pilas) {
        System.out.println();
        for (PilaCoin pila : pilas) {
            if (pila.getId() == id) {
                return pila;
            }
        }
        return null;
    }

    @Transactional
    @RequestMapping("trans_pila_rede.adm")
    public RedirectView transfPilaRede(RedirectAttributes redirectAttributes, String idDestino, int idPila, ServletRequest request) throws Exception {
        try {
            System.out.println(idPila);
            pilaDHTClient = new PilaDHTClient("192.168.90.194", 4001, usuario);
            Usuario usuarioDestino = pilaDHTClient.getUsuario(idDestino);
            usuario = pilaDHTClient.getUsuario("id-anderson");
            Transacao transacao = new Transacao();
            transacao.setIdNovoDono(usuarioDestino.getId());
            transacao.setDataTransacao(new Date());
            transacao.setChaveNovoDono(usuarioDestino.getChavePublica());
            PilaCoin pilaCoin = getPilaSet(idPila, usuario.getMeusPilas());


            byte[] transacaoSerializada = Utils.serializa(transacao);
            byte[] hashTransacao = Utils.hashObject(transacaoSerializada, "SHA-256");

            transacao.setAssinaturaDono(Utils.criptografar("RSA", RSAUtil.getPrivateKey("chave_privada"), hashTransacao));
            ArrayList<Transacao> transacoes = new ArrayList();
            transacoes.add(transacao);
            pilaCoin.setTransacoes(transacoes);
            pilaDHTClient.setPilaCoin(pilaCoin);
            redirectAttributes.addFlashAttribute("mensagem", "PilaCoin transferido com sucesso para " + idDestino);
            redirectAttributes.addFlashAttribute("tipo", "success");


            Log log = new Log();
            log.setDataHora(new Date());
            log.setClasse(Transacao.class);
            log.setIdObjeto(pilaCoin.getId());
            log.setTipo("transferencia");
            this.geraLog(log);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro na transferencia. Tente novamente");
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }



        return new RedirectView("usuarios_rede.priv", true);
    }


    @RequestMapping("usuarios_rede.priv")
    public String getUsuariosRede(Model model) throws IOException, ClassNotFoundException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        pilaDHTClient = new PilaDHTClient("192.168.90.194", 4001, usuario);
        for (String idUsuario : DadosServidor.getUsuarios()) {
            Usuario usuario = pilaDHTClient.getUsuario(idUsuario);
            usuario.setId(usuario.getId().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
            if (usuario != null) {
                usuarios.add(usuario);
            }
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("userAdmin", pilaDHTClient.getUsuario("id-anderson"));
        return "usuarios-rede";
    }


    public void geraLog(Log log) {
        hibernateDAO.criaObjeto(log);
    }
}