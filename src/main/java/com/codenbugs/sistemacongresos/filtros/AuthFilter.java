package com.codenbugs.sistemacongresos.filtros;

import com.codenbugs.sistemacongresos.modelos.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = {
    "/admin/*", "/organizador/*", "/participante/*"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession sesion = req.getSession(false);
        Usuario u = (sesion != null) ? (Usuario) sesion.getAttribute("usuario") : null;

        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String path = req.getRequestURI();

        // Rutas admin requieren admin de sistema
        if (path.contains("/admin/") && !u.isEsAdministradorSistema()) {
            // Si no es admin del sistema, lo movemos a su dashboard por defecto
            resp.sendRedirect(req.getContextPath() + "/organizador/dashboard");
            return;
        }
        /*
        Si quieremos validar organizador/participante con más granularidad,
        aquí puedemos consultar BD o sesión para saber si el usuario es organizador
         */
        chain.doFilter(request, response);
    }
}
