package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.*;

public class ParticipacionDAO {

    private final DBConnection DB;

    public ParticipacionDAO(DBConnection db) {
        this.DB = db;
    }

    // Verificamos si ya está inscrito el usuario en el congreso
    public boolean yaInscrito(Connection cn, int userId, int congresoId) throws Exception {
        String sql = "SELECT 1 FROM participantes_congresos WHERE usuario_id=? AND congreso_id=? LIMIT 1";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, congresoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Insertamos la inscripción como pagada
    public int insertarInscripcion(Connection cn, int userId, int congresoId, java.math.BigDecimal monto) throws Exception {
        String sql = "INSERT INTO participantes_congresos (usuario_id, congreso_id, pago_realizado, monto_pagado, fecha_pago) "
                + "VALUES (?,?,?,?, NOW())";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setInt(2, congresoId);
            ps.setBoolean(3, true);
            ps.setBigDecimal(4, monto);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return 0;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : rows;
            }
        }
    }
}
