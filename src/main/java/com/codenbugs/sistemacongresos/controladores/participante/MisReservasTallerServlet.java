package com.codenbugs.sistemacongresos.controladores.participante;

import com.codenbugs.sistemacongresos.dao.ReservaActividadDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "MisReservasTallerServlet", urlPatterns = {"/participante/talleres/mis"})
public class MisReservasTallerServlet extends HttpServlet {

    private ReservaActividadDAO reservaDAO;

    @Override
    public void init() {
        reservaDAO = new ReservaActividadDAO(new DBConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        try {
            req.setAttribute("misReservas", reservaDAO.listarReservasUsuario(u.getId()));
            req.getRequestDispatcher("/vistas/participante/talleres/mis_reservas.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error listando mis reservas", e);
        }
    }
}
