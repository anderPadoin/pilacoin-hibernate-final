package br.ufsm.csi.seguranca.pila.controller;

import br.ufsm.csi.seguranca.pila.service.MineracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

@Repository
@Controller
public class MineracaoController {

    DatagramSocket socket;

    @Autowired
    MineracaoService mineracaoService;


    public MineracaoController() throws SocketException {
        this.socket = new DatagramSocket();

    }
    @RequestMapping("switchmineracao.adm")
    @Transactional
    public RedirectView switchMineracao(RedirectAttributes redirectAttributes) throws SocketException {
        redirectAttributes.addFlashAttribute("serverFound", mineracaoService.switchMineracao());
        return new RedirectView("mineracao.adm", true);
    }

    @RequestMapping("mineracao.adm")
    public String mineracao(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("status", mineracaoService.isStatus());
        model.addAllAttributes(redirectAttributes.getFlashAttributes());
        return "mineracao";
    }



}
