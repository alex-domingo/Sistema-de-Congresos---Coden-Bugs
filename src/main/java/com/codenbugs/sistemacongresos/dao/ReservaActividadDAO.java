package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaActividadDAO {

    private final DBConnection DB;

    public ReservaActividadDAO(DBConnection db) {
        this.DB = db;
    }

    /*
    El usuario ya reservó ese taller? (usamos la misma CN transaccional cuando se
    llame dentro de una transacción)
     */
    public boolean yaReservado(Connection cn, int userId, int actividadId) throws Exception {
        String sql = "SELECT 1 FROM inscripciones_actividades WHERE usuario_id=? AND actividad_id=? LIMIT 1";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, actividadId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Número de reservas actuales en la actividad
    public int contarReservas(Connection cn, int actividadId) throws Exception {
        String sql = "SELECT COUNT(*) FROM inscripciones_actividades WHERE actividad_id=?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, actividadId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    // Insertamos la reserva
    public int crearReserva(Connection cn, int userId, int actividadId) throws Exception {
        String sql = "INSERT INTO inscripciones_actividades (usuario_id, actividad_id, fecha_inscripcion) "
                + "VALUES (?,?, NOW())";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setInt(2, actividadId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return 0;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : rows;
            }
        }
    }

    /*
    Lista textual sencilla de reservas del usuario (JOIN para mostrar nombre
    + congreso).
     */
    public List<String> listarReservasUsuario(int userId) throws Exception {
        String sql = "SELECT a.nombre AS actividad, c.titulo AS congreso, a.fecha_hora_inicio, a.fecha_hora_fin, a.salon_id "
                + "FROM inscripciones_actividades ia "
                + "JOIN actividades a ON a.id = ia.actividad_id "
                + "JOIN congresos c ON c.id = a.congreso_id "
                + "WHERE ia.usuario_id = ? "
                + "ORDER BY a.fecha_hora_inicio ASC";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                List<String> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(
                            rs.getString("congreso") + " — "
                            + rs.getString("actividad") + " | "
                            + rs.getTimestamp("fecha_hora_inicio") + " → "
                            + rs.getTimestamp("fecha_hora_fin") + " | Salón "
                            + rs.getInt("salon_id")
                    );
                }
                return out;
            }
        }
    }
}
