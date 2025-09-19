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

@WebServlet(name = "ListarCongresosServlet", urlPatterns = {"/organizador/congresos"})
public class ListarCongresosServlet extends HttpServlet {

    private CongresoServicio servicio;

    @Override
    public void init() {
        DBConnection db = new DBConnection();
        servicio = new CongresoServicio(new CongresoDAO(db), new AdministradorInstitucionDAO(db));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        try {
            req.setAttribute("congresos", servicio.listarPorOrganizador(u.getId()));
            req.getRequestDispatcher("/vistas/organizador/congresos/lista.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error listando congresos", e);
        }
    }
}
