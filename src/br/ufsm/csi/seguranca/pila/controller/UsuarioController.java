package br.ufsm.csi.seguranca.pila.controller;

import br.ufsm.csi.seguranca.pila.dao.HibernateDAO;
import br.ufsm.csi.seguranca.pila.model.Log;
import br.ufsm.csi.seguranca.pila.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by cpol on 29/05/2017.
 */
@Controller
public class UsuarioController {

    private boolean minerando = false;
    private boolean bloqueado = false;
    private Date horaBloqueio;
    private int count = 0;

    @Autowired
    private HibernateDAO hibernateDAO;

    @Transactional
    @RequestMapping("cria-usuario.adm")
    public RedirectView criaUsuario(Model model, User user, String senhaStr, RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        user.setSenha(md.digest(senhaStr.getBytes("ISO-8859-1")));
        try {
            hibernateDAO.criaObjeto(user);
            redirectAttributes.addFlashAttribute("mensagem", "Ususario cadastrado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erros no cadastro. Tente novamente");
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return new RedirectView("lista-usuarios.priv", true);
    }

    @Transactional
    @RequestMapping("login.html")
    public String login(String login, String senha, ServletRequest request, Model model) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        Map<String, Object> map = new HashMap<>();
//        map.put("login", login);
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        map.put("senha", md.digest(senha.getBytes("ISO-8859-1")));
//        Collection usuarios = hibernateDAO.listaObjetosEquals(User.class, map);
//        if (usuarios == null || usuarios.isEmpty()) {
//            return "acesso-negado";
//        } else {
//            return "ok";
//        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        User user = hibernateDAO.findUsuarioHQL(login, senha);
        if (this.bloqueado) {
            if ((this.horaBloqueio.getTime() / 1000) - (new Date().getTime() / 1000) <= -15) {
                this.bloqueado = false;
                this.count = 0;
                this.horaBloqueio = null;
            }
        }
        if (this.bloqueado) {

            model.addAttribute("mensagem", "Muitas tentativas de login. Tente novamente mais tarde");
            model.addAttribute("tipo", "warning");
            return "login";
        }
        if (user == null) {
            this.count++;
            if (this.count >= 5) {
                this.bloqueado = true;
                this.horaBloqueio = new Date();
            }
            model.addAttribute("mensagem", "Dados incorretos");
            model.addAttribute("tipo", "danger");
            return "login";
        } else {
            this.count = 0;
            this.bloqueado = false;
            this.horaBloqueio = null;
            httpServletRequest.getSession().invalidate();
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("usuario", user);
            httpServletRequest.getSession().setAttribute("usuario", user);

            Log log = new Log();
            log.setUser(user);
            log.setIdObjeto(user.getId());
            log.setTipo("login");
            log.setClasse(User.class);
            log.setDataHora(new Date());
            hibernateDAO.criaObjeto(log);

            return "hello";
        }
    }

    @Transactional
    @RequestMapping("cria-log.priv")
    public String criaLog(Long idUsuario,
                          Long idObjeto,
                          String classe,
                          @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date dataHora) throws ClassNotFoundException {
        User user = (User) hibernateDAO.carregaObjeto(User.class, idUsuario);
        Log log = new Log();
        log.setClasse(Class.forName(classe));
        log.setIdObjeto(idObjeto);
        log.setDataHora(dataHora);
        log.setUser(user);
        hibernateDAO.criaObjeto(log);
        return "log";
    }

    @Transactional
    @RequestMapping("lista-usuarios.adm")
    public String listaUsuarios(Model model, String nome, String login, RedirectAttributes redirectAttributes) {
        Map<String, String> m = new HashMap<>();
        if (nome != null && !nome.isEmpty()) {
            m.put("nome", nome);
        }
        if (login != null && !login.isEmpty()) {
            m.put("login", login);
        }
        model.addAllAttributes(redirectAttributes.getFlashAttributes());
        model.addAttribute("usuarios", hibernateDAO.listaObjetos(User.class, m, null, null, false));
        return "usuarios-sistema";
    }


    @RequestMapping("logout.priv")
    public String logOut(ServletRequest request) {
        ((HttpServletRequest) request).getSession().invalidate();
        return "../../index";
    }

    @RequestMapping("criar_conta.adm")
    public String criarConta() {
        return "criar-conta";
    }

}
