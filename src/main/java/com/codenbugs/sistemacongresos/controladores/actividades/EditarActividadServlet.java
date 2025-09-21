package com.codenbugs.sistemacongresos.controladores.actividades;

import com.codenbugs.sistemacongresos.dao.*;
import com.codenbugs.sistemacongresos.modelos.Actividad;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.ActividadServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "EditarActividadServlet", urlPatterns = {"/organizador/actividades/editar"})
public class EditarActividadServlet extends HttpServlet {

    private ActividadServicio servicio;
    private CongresoDAO congresoDAO;
    private SalonDAO salonDAO;

    @Override
    public void init() {
        var db = new DBConnection();
        servicio = new ActividadServicio(new ActividadDAO(db), new SalonDAO(db), new CongresoDAO(db));
        congresoDAO = new CongresoDAO(db);
        salonDAO = new SalonDAO(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Actividad a = servicio.buscar(id);
            if (a == null) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("actividad", a);
            req.setAttribute("congresos", congresoDAO.listarPorOrganizador(u.getId()));
            req.setAttribute("salones", salonDAO.listarPorCongreso(a.getCongresoId()));
            req.getRequestDispatcher("/vistas/organizador/actividades/form.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error obteniendo actividad", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        Actividad a = new Actividad();
        a.setId(Integer.valueOf(req.getParameter("id")));
        a.setNombre(req.getParameter("nombre"));
        a.setDescripcion(req.getParameter("descripcion"));
        a.setTipoActividad(req.getParameter("tipo_actividad"));
        a.setFechaHoraInicio(Timestamp.valueOf(req.getParameter("fecha_inicio") + " " + req.getParameter("hora_inicio") + ":00"));
        a.setFechaHoraFin(Timestamp.valueOf(req.getParameter("fecha_fin") + " " + req.getParameter("hora_fin") + ":00"));
        a.setCongresoId(Integer.valueOf(req.getParameter("congreso_id")));
        a.setSalonId(Integer.valueOf(req.getParameter("salon_id")));
        String cupo = req.getParameter("cupo_maximo");
        a.setCupoMaximo((cupo != null && !cupo.isBlank()) ? Integer.valueOf(cupo) : null);

        try {
            servicio.actualizar(u.getId(), a);
            resp.sendRedirect(req.getContextPath() + "/organizador/actividades?congresoId=" + a.getCongresoId());
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("actividad", a);
            try {
                req.setAttribute("congresos", congresoDAO.listarPorOrganizador(u.getId()));
                req.setAttribute("salones", salonDAO.listarPorCongreso(a.getCongresoId()));
            } catch (Exception ignored) {
            }
            req.getRequestDispatcher("/vistas/organizador/actividades/form.jsp").forward(req, resp);
        }
    }
}
