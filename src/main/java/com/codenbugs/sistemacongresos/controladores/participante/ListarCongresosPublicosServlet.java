package com.codenbugs.sistemacongresos.controladores.participante;

import com.codenbugs.sistemacongresos.dao.CongresoPublicoDAO;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ListarCongresosPublicosServlet", urlPatterns = {"/participante/congresos"})
public class ListarCongresosPublicosServlet extends HttpServlet {

    private CongresoPublicoDAO dao;

    @Override
    public void init() {
        dao = new CongresoPublicoDAO(new DBConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("congresos", dao.listarActivos());
            req.getRequestDispatcher("/vistas/participante/congresos/lista.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error listando congresos", e);
        }
    }
}
