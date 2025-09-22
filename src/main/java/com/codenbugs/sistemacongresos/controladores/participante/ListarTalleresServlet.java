package com.codenbugs.sistemacongresos.controladores.participante;

import com.codenbugs.sistemacongresos.dao.ActividadPublicaDAO;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ListarTalleresServlet", urlPatterns = {"/participante/talleres"})
public class ListarTalleresServlet extends HttpServlet {

    private ActividadPublicaDAO actividadDAO;

    @Override
    public void init() {
        actividadDAO = new ActividadPublicaDAO(new DBConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var u = (com.codenbugs.sistemacongresos.modelos.Usuario) req.getSession().getAttribute("usuario");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        try {
            var actividades = actividadDAO.listarActividadesDisponiblesParaUsuario(u.getId());
            req.setAttribute("actividades", actividades);
            req.getRequestDispatcher("/vistas/participante/talleres/lista.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error listando actividades", e);
        }
    }
}
