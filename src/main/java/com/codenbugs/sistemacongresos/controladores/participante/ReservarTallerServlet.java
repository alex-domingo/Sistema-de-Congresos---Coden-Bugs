package com.codenbugs.sistemacongresos.controladores.participante;

import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.ReservaTallerServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ReservarTallerServlet", urlPatterns = {"/participante/talleres/reservar"})
public class ReservarTallerServlet extends HttpServlet {

    private ReservaTallerServicio servicio;

    @Override
    public void init() {
        servicio = new ReservaTallerServicio(new DBConnection());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        int actividadId = Integer.parseInt(req.getParameter("actividad_id"));
        try {
            servicio.reservar(u.getId(), actividadId);
            req.getSession().setAttribute("flash_ok", "¡Reserva confirmada!");
        } catch (Exception e) {
            req.getSession().setAttribute("flash_error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/participante/talleres");
    }
}
