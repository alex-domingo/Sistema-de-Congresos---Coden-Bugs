package com.codenbugs.sistemacongresos.controladores.instituciones;

import com.codenbugs.sistemacongresos.dao.InstitucionDAO;
import com.codenbugs.sistemacongresos.modelos.Institucion;
import com.codenbugs.sistemacongresos.servicios.InstitucionServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "EditarInstitucionServlet", urlPatterns = {"/admin/instituciones/editar"})
public class EditarInstitucionServlet extends HttpServlet {

    private InstitucionServicio servicio;

    @Override
    public void init() {
        servicio = new InstitucionServicio(new InstitucionDAO(new DBConnection()));
    }

    // GET: mostramos formulario con los datos existentes
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro id");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException nfe) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            return;
        }

        try {
            Institucion i = servicio.buscar(id);
            if (i == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Institución no encontrada");
                return;
            }
            req.setAttribute("inst", i);
            req.getRequestDispatcher("/vistas/administrador/instituciones/form.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error obteniendo institución", e);
        }
    }

    // POST: guardamos los cambios
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        boolean activa = "on".equals(req.getParameter("activa"));

        Integer id = null;
        try {
            if (idStr == null) {
                throw new IllegalArgumentException("Falta parámetro id");
            }
            id = Integer.parseInt(idStr);

            // Actualizamos
            servicio.actualizar(id, nombre, descripcion, activa);
            resp.sendRedirect(req.getContextPath() + "/admin/instituciones");
        } catch (Exception e) {
            // Si existe algún error, re-poblamos el formulario
            req.setAttribute("error", e.getMessage());

            try {
                if (id != null) {
                    // Si el id es válido, intento cargar desde BD para re-mostrar datos actuales
                    Institucion existente = servicio.buscar(id);
                    if (existente != null) {
                        // Sobrescribe con lo que el usuario envió para no perder cambios en pantalla
                        existente.setNombre(nombre);
                        existente.setDescripcion(descripcion);
                        existente.setActiva(activa);
                        req.setAttribute("inst", existente);
                    } else {
                        // Si no existe, construimos uno temporal
                        req.setAttribute("inst", construirTemporal(id, nombre, descripcion, activa));
                    }
                } else {
                    // Si el id es inválido, construimos uno temporal sin id
                    req.setAttribute("inst", construirTemporal(null, nombre, descripcion, activa));
                }
            } catch (Exception ignore) {
                // Si incluso al buscar falla, mostramos lo temporal
                req.setAttribute("inst", construirTemporal(id, nombre, descripcion, activa));
            }

            req.getRequestDispatcher("/vistas/administrador/instituciones/form.jsp").forward(req, resp);
        }
    }

    private Institucion construirTemporal(Integer id, String nombre, String descripcion, boolean activa) {
        Institucion i = new Institucion();
        i.setId(id);
        i.setNombre(nombre);
        i.setDescripcion(descripcion);
        i.setActiva(activa);
        return i;
    }
}
