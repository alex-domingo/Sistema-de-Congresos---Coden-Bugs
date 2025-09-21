package com.codenbugs.sistemacongresos.controladores.salones;

import com.codenbugs.sistemacongresos.dao.CongresoDAO;
import com.codenbugs.sistemacongresos.dao.SalonDAO;
import com.codenbugs.sistemacongresos.modelos.Salon;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.SalonServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;

@WebServlet(name = "EditarSalonServlet", urlPatterns = {"/organizador/salones/editar"})
public class EditarSalonServlet extends HttpServlet {

    private SalonServicio servicio;
    private CongresoDAO congresoDAO;

    @Override
    public void init() {
        var db = new DBConnection();
        congresoDAO = new CongresoDAO(db);
        servicio = new SalonServicio(new SalonDAO(db), congresoDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Salon s = servicio.buscar(id);
            if (s == null) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("salon", s);
            req.setAttribute("congresos", congresoDAO.listarPorOrganizador(u.getId()));
            req.getRequestDispatcher("/vistas/organizador/salones/form.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error obteniendo salón", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        Salon s = new Salon();
        s.setId(Integer.valueOf(req.getParameter("id")));
        s.setNombre(req.getParameter("nombre"));
        s.setUbicacion(req.getParameter("ubicacion"));
        s.setDescripcion(req.getParameter("descripcion"));
        String cap = req.getParameter("capacidad");
        s.setCapacidad((cap != null && !cap.isBlank()) ? Integer.valueOf(cap) : null);
        s.setCongresoId(Integer.valueOf(req.getParameter("congreso_id")));

        try {
            servicio.actualizar(u.getId(), s);
            resp.sendRedirect(req.getContextPath() + "/organizador/salones");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("salon", s);
            try {
                req.setAttribute("congresos", congresoDAO.listarPorOrganizador(u.getId()));
            } catch (Exception ignored) {
            }
            req.getRequestDispatcher("/vistas/organizador/salones/form.jsp").forward(req, resp);
        }
    }
}
