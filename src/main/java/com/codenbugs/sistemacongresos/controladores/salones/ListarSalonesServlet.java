package com.codenbugs.sistemacongresos.controladores.salones;

import com.codenbugs.sistemacongresos.dao.CongresoDAO;
import com.codenbugs.sistemacongresos.dao.SalonDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.SalonServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;

@WebServlet(name = "ListarSalonesServlet", urlPatterns = {"/organizador/salones"})
public class ListarSalonesServlet extends HttpServlet {

    private SalonServicio servicio;

    @Override
    public void init() {
        var db = new DBConnection();
        servicio = new SalonServicio(new SalonDAO(db), new CongresoDAO(db));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        try {
            req.setAttribute("salones", servicio.listarPorOrganizador(u.getId()));
            req.getRequestDispatcher("/vistas/organizador/salones/lista.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error listando salones", e);
        }
    }
}
