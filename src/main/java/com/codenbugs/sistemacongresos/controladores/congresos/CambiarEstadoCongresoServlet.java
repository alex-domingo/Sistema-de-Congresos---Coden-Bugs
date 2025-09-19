package com.codenbugs.sistemacongresos.controladores.congresos;

import com.codenbugs.sistemacongresos.dao.AdministradorInstitucionDAO;
import com.codenbugs.sistemacongresos.dao.CongresoDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.CongresoServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "CambiarEstadoCongresoServlet", urlPatterns = {"/organizador/congresos/estado"})
public class CambiarEstadoCongresoServlet extends HttpServlet {

    private CongresoServicio servicio;

    @Override
    public void init() {
        DBConnection db = new DBConnection();
        servicio = new CongresoServicio(new CongresoDAO(db), new AdministradorInstitucionDAO(db));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        int id = Integer.parseInt(req.getParameter("id"));
        boolean activo = Boolean.parseBoolean(req.getParameter("activo")); // Puede ser true o false
        try {
            servicio.cambiarEstado(u.getId(), id, activo);
            resp.sendRedirect(req.getContextPath() + "/organizador/congresos");
        } catch (Exception e) {
            throw new ServletException("Error cambiando estado del congreso", e);
        }
    }
}
