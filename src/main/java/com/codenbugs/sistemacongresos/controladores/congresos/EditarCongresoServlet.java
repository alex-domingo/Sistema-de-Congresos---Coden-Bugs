package com.codenbugs.sistemacongresos.controladores.congresos;

import com.codenbugs.sistemacongresos.dao.AdministradorInstitucionDAO;
import com.codenbugs.sistemacongresos.dao.CongresoDAO;
import com.codenbugs.sistemacongresos.modelos.Congreso;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.CongresoServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

@WebServlet(name = "EditarCongresoServlet", urlPatterns = {"/organizador/congresos/editar"})
public class EditarCongresoServlet extends HttpServlet {

    private CongresoServicio servicio;
    private AdministradorInstitucionDAO adminDAO;

    @Override
    public void init() {
        DBConnection db = new DBConnection();
        adminDAO = new AdministradorInstitucionDAO(db);
        servicio = new CongresoServicio(new CongresoDAO(db), adminDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendError(400, "Falta id");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.sendError(400, "ID inválido");
            return;
        }

        try {
            Congreso c = servicio.buscar(id, u.getId());
            if (c == null) {
                resp.sendError(404, "Congreso no encontrado");
                return;
            }
            req.setAttribute("congreso", c);
            req.setAttribute("instituciones", adminDAO.institucionesDelUsuario(u.getId()));
            req.getRequestDispatcher("/vistas/organizador/congresos/form.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error obteniendo congreso", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        Integer id = null;
        try {
            id = Integer.parseInt(req.getParameter("id"));
            String titulo = req.getParameter("titulo");
            String descripcion = req.getParameter("descripcion");
            String ubicacion = req.getParameter("ubicacion");
            Date inicio = Date.valueOf(req.getParameter("fecha_inicio"));
            Date fin = Date.valueOf(req.getParameter("fecha_fin"));
            BigDecimal precio = new BigDecimal(req.getParameter("precio"));
            Integer institucionId = Integer.parseInt(req.getParameter("institucion_id"));
            Boolean activo = "on".equals(req.getParameter("activo"));
            BigDecimal comision = null;
            if (req.getParameter("comision") != null && !req.getParameter("comision").isBlank()) {
                comision = new BigDecimal(req.getParameter("comision"));
            }

            servicio.actualizar(u.getId(), id, titulo, descripcion, inicio, fin, precio, ubicacion, institucionId, activo, comision);
            resp.sendRedirect(req.getContextPath() + "/organizador/congresos");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            try {
                if (id != null) {
                    req.setAttribute("congreso", servicio.buscar(id, u.getId()));
                }
                req.setAttribute("instituciones", adminDAO.institucionesDelUsuario(u.getId()));
            } catch (Exception ignored) {
            }
            req.getRequestDispatcher("/vistas/organizador/congresos/form.jsp").forward(req, resp);
        }
    }
}
