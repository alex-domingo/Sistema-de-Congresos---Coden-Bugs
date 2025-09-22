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
        try {
            req.setAttribute("talleres", actividadDAO.listarTalleresDisponibles());
            req.getRequestDispatcher("/vistas/participante/talleres/lista.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error listando talleres", e);
        }
    }
}
