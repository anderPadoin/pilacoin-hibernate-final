package br.ufsm.csi.seguranca.pila.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by cpol on 22/05/2017.
 */
@Controller
public class HelloController {

    @RequestMapping("hello.priv")
    public String hello(Model model) {
        model.addAttribute("sts", "Mineração parada");
        return "hello";
    }

    @RequestMapping("/")
    public String redirectHello(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getSession().getAttribute("usuario") != null) {
            return "redirect:hello.priv";
        } else {
            return "index";
        }
    }

    @RequestMapping("logar.html")
    public String logar() {
        return "login";
    }


}
