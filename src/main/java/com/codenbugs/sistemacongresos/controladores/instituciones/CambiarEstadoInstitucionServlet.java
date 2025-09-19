package com.codenbugs.sistemacongresos.controladores.instituciones;

import com.codenbugs.sistemacongresos.dao.InstitucionDAO;
import com.codenbugs.sistemacongresos.servicios.InstitucionServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "CambiarEstadoInstitucionServlet", urlPatterns = {"/admin/instituciones/estado"})
public class CambiarEstadoInstitucionServlet extends HttpServlet {

    private InstitucionServicio servicio;

    @Override
    public void init() {
        servicio = new InstitucionServicio(new InstitucionDAO(new DBConnection()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean activa = Boolean.parseBoolean(req.getParameter("activa")); // Sabemos que puede ser true o false
        try {
            if (activa) {
                servicio.activar(id);
            } else {
                servicio.desactivar(id);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/instituciones");
        } catch (Exception e) {
            throw new ServletException("Error cambiando estado", e);
        }
    }
}
