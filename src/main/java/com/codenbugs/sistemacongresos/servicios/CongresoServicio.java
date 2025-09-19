package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.AdministradorInstitucionDAO;
import com.codenbugs.sistemacongresos.dao.CongresoDAO;
import com.codenbugs.sistemacongresos.modelos.Congreso;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class CongresoServicio {

    private final CongresoDAO DAO;
    private final AdministradorInstitucionDAO ADMIN_INST_DAO;

    public CongresoServicio(CongresoDAO dao, AdministradorInstitucionDAO adminInstDAO) {
        this.DAO = dao;
        this.ADMIN_INST_DAO = adminInstDAO;
    }

    public List<Congreso> listarPorOrganizador(int usuarioId) throws Exception {
        return DAO.listarPorOrganizador(usuarioId);
    }

    public Congreso buscar(int id, int usuarioId) throws Exception {
        if (!DAO.perteneceAOrganizador(id, usuarioId)) {
            return null;
        }
        return DAO.buscarPorId(id);
    }

    public int crear(int usuarioId, String titulo, String descripcion,
            Date fechaInicio, Date fechaFin, BigDecimal precio,
            String ubicacion, Integer institucionId, Boolean activo,
            BigDecimal comisionPct) throws Exception {

        validarBasicos(titulo, fechaInicio, fechaFin, precio, ubicacion);
        // Por seguridad el organizador debe administrar esa institución
        if (!ADMIN_INST_DAO.institucionesDelUsuario(usuarioId).contains(institucionId)) {
            throw new IllegalArgumentException("No puede crear congresos en una institución que no administra.");
        }

        Congreso c = new Congreso();
        c.setTitulo(titulo.trim());
        c.setDescripcion(descripcion != null ? descripcion.trim() : null);
        c.setFechaInicio(fechaInicio);
        c.setFechaFin(fechaFin);
        c.setPrecio(precio);
        c.setUbicacion(ubicacion.trim());
        c.setInstitucionId(institucionId);
        c.setActivo(activo != null ? activo : true);
        c.setComisionPorcentaje(comisionPct);

        return DAO.crear(c);
    }

    public boolean actualizar(int usuarioId, int id, String titulo, String descripcion,
            Date fechaInicio, Date fechaFin, BigDecimal precio,
            String ubicacion, Integer institucionId, Boolean activo,
            BigDecimal comisionPct) throws Exception {

        validarBasicos(titulo, fechaInicio, fechaFin, precio, ubicacion);
        if (!DAO.perteneceAOrganizador(id, usuarioId)) {
            throw new IllegalArgumentException("No puede editar este congreso.");
        }
        // También validamos que no cambie a una institución ajena
        if (!ADMIN_INST_DAO.institucionesDelUsuario(usuarioId).contains(institucionId)) {
            throw new IllegalArgumentException("No puede asignar a una institución que no administra.");
        }

        Congreso c = new Congreso();
        c.setId(id);
        c.setTitulo(titulo.trim());
        c.setDescripcion(descripcion != null ? descripcion.trim() : null);
        c.setFechaInicio(fechaInicio);
        c.setFechaFin(fechaFin);
        c.setPrecio(precio);
        c.setUbicacion(ubicacion.trim());
        c.setInstitucionId(institucionId);
        c.setActivo(activo != null ? activo : true);
        c.setComisionPorcentaje(comisionPct);

        return DAO.actualizar(c);
    }

    public boolean cambiarEstado(int usuarioId, int id, boolean activo) throws Exception {
        if (!DAO.perteneceAOrganizador(id, usuarioId)) {
            throw new IllegalArgumentException("No puede cambiar el estado de este congreso.");
        }
        return DAO.cambiarEstado(id, activo);
    }

    private void validarBasicos(String titulo, Date inicio, Date fin,
            BigDecimal precio, String ubicacion) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (ubicacion == null || ubicacion.isBlank()) {
            throw new IllegalArgumentException("La ubicación es obligatoria.");
        }
        if (inicio == null || fin == null) {
            throw new IllegalArgumentException("Las fechas son obligatorias.");
        }
        if (inicio.after(fin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la de fin.");
        }
        if (precio == null || precio.compareTo(new BigDecimal("35.00")) < 0) {
            throw new IllegalArgumentException("El precio debe ser ≥ Q35.00.");
        }
    }
}
