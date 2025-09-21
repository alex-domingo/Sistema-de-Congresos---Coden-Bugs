package com.codenbugs.sistemacongresos.controladores.actividades;

import com.codenbugs.sistemacongresos.dao.*;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.ActividadServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;

@WebServlet(name = "ListarActividadesServlet", urlPatterns = {"/organizador/actividades"})
public class ListarActividadesServlet extends HttpServlet {

    private ActividadServicio servicio;
    private CongresoDAO congresoDAO;

    @Override
    public void init() {
        var db = new DBConnection();
        servicio = new ActividadServicio(new ActividadDAO(db), new SalonDAO(db), new CongresoDAO(db));
        congresoDAO = new CongresoDAO(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        try {
            String congresoIdStr = req.getParameter("congresoId");
            if (congresoIdStr != null && !congresoIdStr.isBlank()) {
                int congresoId = Integer.parseInt(congresoIdStr);
                req.setAttribute("actividades", servicio.listarPorCongreso(congresoId));
                req.setAttribute("congresoIdSel", congresoId);
            }
            req.setAttribute("congresos", congresoDAO.listarPorOrganizador(u.getId()));
            req.getRequestDispatcher("/vistas/organizador/actividades/lista.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error listando actividades", e);
        }
    }
}
