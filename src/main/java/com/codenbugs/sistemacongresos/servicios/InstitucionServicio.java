package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.InstitucionDAO;
import com.codenbugs.sistemacongresos.modelos.Institucion;

import java.util.List;

public class InstitucionServicio {

    private final InstitucionDAO DAO;

    public InstitucionServicio(InstitucionDAO dao) {
        this.DAO = dao;
    }

    public List<Institucion> listar() throws Exception {
        return DAO.listar();
    }

    public Institucion buscar(int id) throws Exception {
        return DAO.buscarPorId(id);
    }

    public int crear(String nombre, String descripcion, boolean activa) throws Exception {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        Institucion i = new Institucion();
        i.setNombre(nombre.trim());
        i.setDescripcion(descripcion != null ? descripcion.trim() : null);
        i.setActiva(activa);
        return DAO.crear(i);
    }

    public boolean actualizar(int id, String nombre, String descripcion, boolean activa) throws Exception {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        Institucion i = DAO.buscarPorId(id);
        if (i == null) {
            throw new IllegalArgumentException("Institución no encontrada");
        }
        i.setNombre(nombre.trim());
        i.setDescripcion(descripcion != null ? descripcion.trim() : null);
        i.setActiva(activa);
        return DAO.actualizar(i);
    }

    public boolean activar(int id) throws Exception {
        return DAO.cambiarEstado(id, true);
    }

    public boolean desactivar(int id) throws Exception {
        return DAO.cambiarEstado(id, false);
    }
}
