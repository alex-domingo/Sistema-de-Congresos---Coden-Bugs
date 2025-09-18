package com.codenbugs.sistemacongresos.filtros;

import com.codenbugs.sistemacongresos.dao.AdministradorInstitucionDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = {
    "/admin/*", "/organizador/*", "/participante/*"
})
public class AuthFilter implements Filter {

    private AdministradorInstitucionDAO adminInstDAO;

    @Override
    public void init(FilterConfig filterConfig) {
        this.adminInstDAO = new AdministradorInstitucionDAO(new DBConnection());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession sesion = req.getSession(false);
        Usuario u = (sesion != null) ? (Usuario) sesion.getAttribute("usuario") : null;

        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String path = req.getRequestURI();
        try {
            if (path.contains("/admin/") && !u.isEsAdministradorSistema()) {
                resp.sendRedirect(req.getContextPath() + "/participante/dashboard");
                return;
            }
            if (path.contains("/organizador/") && !adminInstDAO.esOrganizador(u.getId())) {
                // Si no es admin ni organizador, pa' su casa
                resp.sendRedirect(req.getContextPath() + "/participante/dashboard");
                return;
            }
        } catch (Exception e) {
            throw new ServletException("Error validando permisos", e);
        }
        chain.doFilter(request, response);
    }
}
