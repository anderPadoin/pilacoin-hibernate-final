package br.ufsm.csi.seguranca.pila.filtros;

import br.ufsm.csi.seguranca.pila.model.User;
import br.ufsm.csi.seguranca.pila.model.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * Created by cpol on 05/06/2017.
 */
@WebFilter("*.adm")
public class FiltroAdmin implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        if (request.getSession().getAttribute("usuario") == null) {
            ((HttpServletResponse) servletResponse).sendRedirect("index.html");
        } else if (!(((User) request.getSession().getAttribute("usuario")).getAdmin()) == true) {
            ((HttpServletResponse) servletResponse).sendRedirect("hello.priv");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
