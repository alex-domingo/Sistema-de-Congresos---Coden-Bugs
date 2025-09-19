package com.codenbugs.sistemacongresos.controladores.instituciones;

import com.codenbugs.sistemacongresos.dao.InstitucionDAO;
import com.codenbugs.sistemacongresos.servicios.InstitucionServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "CrearInstitucionServlet", urlPatterns = {"/admin/instituciones/crear"})
public class CrearInstitucionServlet extends HttpServlet {

    private InstitucionServicio servicio;

    @Override
    public void init() {
        servicio = new InstitucionServicio(new InstitucionDAO(new DBConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/vistas/administrador/instituciones/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        boolean activa = "on".equals(req.getParameter("activa"));

        try {
            servicio.crear(nombre, descripcion, activa);
            resp.sendRedirect(req.getContextPath() + "/admin/instituciones");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("nombre", nombre);
            req.setAttribute("descripcion", descripcion);
            req.setAttribute("activa", activa);
            req.getRequestDispatcher("/vistas/administrador/instituciones/form.jsp").forward(req, resp);
        }
    }
}
