package com.codenbugs.sistemacongresos.controladores.participante;

import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.InscripcionServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "InscribirseCongresoServlet", urlPatterns = {"/participante/congresos/inscribir"})
public class InscribirseCongresoServlet extends HttpServlet {

    private InscripcionServicio servicio;

    @Override
    public void init() {
        servicio = new InscripcionServicio(new DBConnection());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        int congresoId = Integer.parseInt(req.getParameter("congreso_id"));
        try {
            servicio.inscribir(u.getId(), congresoId);
            req.getSession().setAttribute("flash_ok", "Inscripción realizada con éxito.");
        } catch (Exception e) {
            req.getSession().setAttribute("flash_error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/participante/congresos");
    }
}
