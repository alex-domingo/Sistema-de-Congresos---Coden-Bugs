package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.ActividadDAO;
import com.codenbugs.sistemacongresos.dao.CongresoDAO;
import com.codenbugs.sistemacongresos.dao.SalonDAO;
import com.codenbugs.sistemacongresos.modelos.Actividad;
import com.codenbugs.sistemacongresos.modelos.Congreso;

import java.sql.Timestamp;
import java.util.List;

public class ActividadServicio {

    private final ActividadDAO ACTIVIDAD_DAO;
    private final SalonDAO SALON_DAO;
    private final CongresoDAO CONGRESO_DAO;

    public ActividadServicio(ActividadDAO actividadDAO, SalonDAO salonDAO, CongresoDAO congresoDAO) {
        this.ACTIVIDAD_DAO = actividadDAO;
        this.SALON_DAO = salonDAO;
        this.CONGRESO_DAO = congresoDAO;
    }

    public List<Actividad> listarPorCongreso(int congresoId) throws Exception {
        return ACTIVIDAD_DAO.listarPorCongreso(congresoId);
    }

    public int crear(int usuarioId, Actividad a) throws Exception {
        validar(a);
        // Seguridad básica
        if (!CONGRESO_DAO.perteneceAOrganizador(a.getCongresoId(), usuarioId)) {
            throw new IllegalArgumentException("No administras el congreso seleccionado.");
        }
        if (!ACTIVIDAD_DAO.salonYCongresoConsistentes(a.getSalonId(), a.getCongresoId())) {
            throw new IllegalArgumentException("El salón no pertenece a ese congreso.");
        }

        // Fechas dentro del congreso
        Congreso c = CONGRESO_DAO.buscarPorId(a.getCongresoId());
        if (c == null) {
            throw new IllegalArgumentException("Congreso no existe.");
        }
        if (a.getFechaHoraInicio().before(Timestamp.valueOf(c.getFechaInicio().toString() + " 00:00:00"))
                || a.getFechaHoraFin().after(Timestamp.valueOf(c.getFechaFin().toString() + " 23:59:59"))) {
            throw new IllegalArgumentException("La actividad debe estar dentro del rango del congreso.");
        }

        // Traslapes
        if (ACTIVIDAD_DAO.existeTraslapeEnSalon(a.getSalonId(), a.getFechaHoraInicio(), a.getFechaHoraFin(), null)) {
            throw new IllegalArgumentException("Existe traslape con otra actividad en el mismo salón.");
        }

        return ACTIVIDAD_DAO.crear(a);
    }

    public boolean actualizar(int usuarioId, Actividad a) throws Exception {
        if (a.getId() == null) {
            throw new IllegalArgumentException("ID requerido.");
        }
        validar(a);
        if (!ACTIVIDAD_DAO.perteneceAOrganizador(a.getId(), usuarioId)) {
            throw new IllegalArgumentException("No puedes editar esta actividad.");
        }
        if (!ACTIVIDAD_DAO.salonYCongresoConsistentes(a.getSalonId(), a.getCongresoId())) {
            throw new IllegalArgumentException("El salón no pertenece a ese congreso.");
        }

        Congreso c = CONGRESO_DAO.buscarPorId(a.getCongresoId());
        if (c == null) {
            throw new IllegalArgumentException("Congreso no existe.");
        }
        if (a.getFechaHoraInicio().before(Timestamp.valueOf(c.getFechaInicio().toString() + " 00:00:00"))
                || a.getFechaHoraFin().after(Timestamp.valueOf(c.getFechaFin().toString() + " 23:59:59"))) {
            throw new IllegalArgumentException("La actividad debe estar dentro del rango del congreso.");
        }

        if (ACTIVIDAD_DAO.existeTraslapeEnSalon(a.getSalonId(), a.getFechaHoraInicio(), a.getFechaHoraFin(), a.getId())) {
            throw new IllegalArgumentException("Existe traslape con otra actividad en el mismo salón.");
        }

        return ACTIVIDAD_DAO.actualizar(a);
    }

    private void validar(Actividad a) {
        if (a.getNombre() == null || a.getNombre().isBlank()) {
            throw new IllegalArgumentException("Nombre obligatorio.");
        }
        if (a.getTipoActividad() == null || a.getTipoActividad().isBlank()) {
            throw new IllegalArgumentException("Tipo obligatorio.");
        }
        if (a.getFechaHoraInicio() == null || a.getFechaHoraFin() == null) {
            throw new IllegalArgumentException("Fechas requeridas.");
        }
        if (!a.getFechaHoraInicio().before(a.getFechaHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la de fin.");
        }
        if (!("PONENCIA".equals(a.getTipoActividad()) || "TALLER".equals(a.getTipoActividad()))) {
            throw new IllegalArgumentException("Tipo inválido.");
        }
        if ("TALLER".equals(a.getTipoActividad())) {
            if (a.getCupoMaximo() == null || a.getCupoMaximo() <= 0) {
                throw new IllegalArgumentException("El taller debe tener un cupo máximo > 0.");
            }
        } else { // PONENCIA
            a.setCupoMaximo(null);
        }
    }

    public Actividad buscar(int id) throws Exception {
        return ACTIVIDAD_DAO.buscarPorId(id);
    }
}
