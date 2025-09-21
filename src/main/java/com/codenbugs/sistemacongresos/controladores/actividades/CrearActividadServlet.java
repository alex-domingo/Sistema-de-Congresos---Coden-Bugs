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

@WebServlet(name = "CrearActividadServlet", urlPatterns = {"/organizador/actividades/crear"})
public class CrearActividadServlet extends HttpServlet {

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
        try {
            req.setAttribute("congresos", congresoDAO.listarPorOrganizador(u.getId()));
            req.getRequestDispatcher("/vistas/organizador/actividades/form.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error preparando formulario", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        Actividad a = new Actividad();
        a.setNombre(req.getParameter("nombre"));
        a.setDescripcion(req.getParameter("descripcion"));
        a.setTipoActividad(req.getParameter("tipo_actividad")); // Puede ser PONENCIA o TALLER
        a.setFechaHoraInicio(Timestamp.valueOf(req.getParameter("fecha_inicio") + " " + req.getParameter("hora_inicio") + ":00"));
        a.setFechaHoraFin(Timestamp.valueOf(req.getParameter("fecha_fin") + " " + req.getParameter("hora_fin") + ":00"));
        a.setCongresoId(Integer.valueOf(req.getParameter("congreso_id")));
        a.setSalonId(Integer.valueOf(req.getParameter("salon_id")));
        String cupo = req.getParameter("cupo_maximo");
        a.setCupoMaximo((cupo != null && !cupo.isBlank()) ? Integer.valueOf(cupo) : null);

        try {
            servicio.crear(u.getId(), a);
            resp.sendRedirect(req.getContextPath() + "/organizador/actividades?congresoId=" + a.getCongresoId());
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            try {
                req.setAttribute("congresos", congresoDAO.listarPorOrganizador(u.getId()));
                req.setAttribute("salones", salonDAO.listarPorCongreso(a.getCongresoId()));
            } catch (Exception ignored) {
            }
            req.setAttribute("actividad", a);
            req.getRequestDispatcher("/vistas/organizador/actividades/form.jsp").forward(req, resp);
        }
    }
}
