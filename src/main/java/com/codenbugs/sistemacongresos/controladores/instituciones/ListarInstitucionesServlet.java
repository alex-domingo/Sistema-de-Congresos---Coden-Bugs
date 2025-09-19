package com.codenbugs.sistemacongresos.controladores.instituciones;

import com.codenbugs.sistemacongresos.dao.InstitucionDAO;
import com.codenbugs.sistemacongresos.servicios.InstitucionServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ListarInstitucionesServlet", urlPatterns = {"/admin/instituciones"})
public class ListarInstitucionesServlet extends HttpServlet {

    private InstitucionServicio servicio;

    @Override
    public void init() {
        servicio = new InstitucionServicio(new InstitucionDAO(new DBConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("instituciones", servicio.listar());
            req.getRequestDispatcher("/vistas/administrador/instituciones/lista.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error listando instituciones", e);
        }
    }
}
