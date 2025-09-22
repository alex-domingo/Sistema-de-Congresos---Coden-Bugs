package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.ActividadPublicaDAO;
import com.codenbugs.sistemacongresos.dao.ReservaActividadDAO;
import com.codenbugs.sistemacongresos.modelos.Actividad;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.Connection;

public class ReservaTallerServicio {

    private final DBConnection DB;
    private final ActividadPublicaDAO ACTIVIDAD_DAO;
    private final ReservaActividadDAO RESERVA_DAO;

    public ReservaTallerServicio(DBConnection db) {
        this.DB = db;
        this.ACTIVIDAD_DAO = new ActividadPublicaDAO(db);
        this.RESERVA_DAO = new ReservaActividadDAO(db);
    }

    /*
    Registramos inscripción a actividad, si es PONENCIA: sin cupo (solo evitamos
    duplicados), si es TALLER: validamos cupo_maximo y evitamos duplicados
     */
    public void reservar(int userId, int actividadId) throws Exception {
        try (Connection cn = DB.getConnection()) {
            cn.setAutoCommit(false);
            try {
                // 1) Actividad válida y congreso activo
                Actividad a = ACTIVIDAD_DAO.buscarActividadActivaPorId(actividadId);
                if (a == null) {
                    throw new IllegalArgumentException("Actividad no disponible.");
                }

                // 2) El usuario debe estar inscrito (pagado) en el congreso de la actividad
                if (!RESERVA_DAO.usuarioInscritoEnCongreso(cn, userId, a.getCongresoId())) {
                    throw new IllegalArgumentException("Debes estar inscrito en el congreso para ver/reservar sus actividades.");
                }

                // 3) Duplicado
                if (RESERVA_DAO.yaReservado(cn, userId, actividadId)) {
                    throw new IllegalArgumentException("Ya tienes esta actividad en tu agenda.");
                }

                // 4) Reglas por tipo
                if ("TALLER".equals(a.getTipoActividad())) {
                    Integer cupoMax = a.getCupoMaximo();
                    if (cupoMax == null || cupoMax <= 0) {
                        throw new IllegalArgumentException("Este taller no tiene cupo configurado.");
                    }
                    int ocupados = RESERVA_DAO.contarReservas(cn, actividadId);
                    if (ocupados >= cupoMax) {
                        throw new IllegalArgumentException("Cupo lleno, ya no hay espacios.");
                    }
                } else if (!"PONENCIA".equals(a.getTipoActividad())) {
                    throw new IllegalArgumentException("Tipo de actividad inválido.");
                }

                // 4) Crear reserva para ambos casos
                int id = RESERVA_DAO.crearReserva(cn, userId, actividadId);
                if (id == 0) {
                    throw new IllegalStateException("No se pudo registrar la reserva.");
                }

                cn.commit();
            } catch (Exception ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }
}
