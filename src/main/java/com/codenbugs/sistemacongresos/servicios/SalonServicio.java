package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.CongresoDAO;
import com.codenbugs.sistemacongresos.dao.SalonDAO;
import com.codenbugs.sistemacongresos.modelos.Salon;

import java.util.List;

public class SalonServicio {

    private final SalonDAO SALON_DAO;
    private final CongresoDAO CONGRESO_DAO;

    public SalonServicio(SalonDAO salonDAO, CongresoDAO congresoDAO) {
        this.SALON_DAO = salonDAO;
        this.CONGRESO_DAO = congresoDAO;
    }

    public List<Salon> listarPorOrganizador(int usuarioId) throws Exception {
        return SALON_DAO.listarPorOrganizador(usuarioId);
    }

    public List<Salon> listarPorCongreso(int congresoId) throws Exception {
        return SALON_DAO.listarPorCongreso(congresoId);
    }

    public int crear(int usuarioId, Salon s) throws Exception {
        if (s.getNombre() == null || s.getNombre().isBlank()) {
            throw new IllegalArgumentException("Nombre obligatorio.");
        }
        if (s.getCongresoId() == null) {
            throw new IllegalArgumentException("Debe elegir congreso.");
        }
        // Por seguridad el congreso debe pertenecer al organizador
        if (!CONGRESO_DAO.perteneceAOrganizador(s.getCongresoId(), usuarioId)) {
            throw new IllegalArgumentException("No puedes crear salones en un congreso que no administras.");
        }
        return SALON_DAO.crear(s);
    }

    public boolean actualizar(int usuarioId, Salon s) throws Exception {
        if (s.getId() == null) {
            throw new IllegalArgumentException("ID requerido.");
        }
        if (!SALON_DAO.perteneceAOrganizador(s.getId(), usuarioId)) {
            throw new IllegalArgumentException("No puedes editar este salón.");
        }
        if (!CONGRESO_DAO.perteneceAOrganizador(s.getCongresoId(), usuarioId)) {
            throw new IllegalArgumentException("No puedes mover el salón a un congreso que no administras.");
        }
        if (s.getNombre() == null || s.getNombre().isBlank()) {
            throw new IllegalArgumentException("Nombre obligatorio.");
        }
        return SALON_DAO.actualizar(s);
    }

    public Salon buscar(int id) throws Exception {
        return SALON_DAO.buscarPorId(id);
    }
}
