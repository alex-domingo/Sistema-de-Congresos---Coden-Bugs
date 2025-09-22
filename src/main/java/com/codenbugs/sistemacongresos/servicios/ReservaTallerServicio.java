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
    Reservamos un taller si hay cupo y no existe reserva previa
     */
    public void reservar(int userId, int actividadId) throws Exception {
        try (Connection cn = DB.getConnection()) {
            cn.setAutoCommit(false);
            try {
                // 1) Verificamos que sea taller en congreso activo
                Actividad a = ACTIVIDAD_DAO.buscarTallerActivoPorId(actividadId);
                if (a == null) {
                    throw new IllegalArgumentException("Taller no disponible.");
                }

                // 2) Manejamos el duplicado
                if (RESERVA_DAO.yaReservado(cn, userId, actividadId)) {
                    throw new IllegalArgumentException("Ya reservaste este taller.");
                }

                // 3) Cupo
                Integer cupoMax = a.getCupoMaximo(); // Debe existir por regla de negocio
                if (cupoMax == null || cupoMax <= 0) {
                    throw new IllegalArgumentException("Este taller no tiene cupo configurado.");
                }
                int ocupados = RESERVA_DAO.contarReservas(cn, actividadId);
                if (ocupados >= cupoMax) {
                    throw new IllegalArgumentException("Cupo lleno, ya no hay espacios.");
                }

                // 4) Manejamos crear reserva
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
