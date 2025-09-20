package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.modelos.Congreso;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongresoPublicoDAO {

    private final DBConnection DB;

    public CongresoPublicoDAO(DBConnection db) {
        this.DB = db;
    }

    private Congreso map(ResultSet rs) throws SQLException {
        Congreso c = new Congreso();
        c.setId(rs.getInt("id"));
        c.setTitulo(rs.getString("titulo"));
        c.setDescripcion(rs.getString("descripcion"));
        c.setFechaInicio(rs.getDate("fecha_inicio"));
        c.setFechaFin(rs.getDate("fecha_fin"));
        c.setPrecio(rs.getBigDecimal("precio"));
        c.setUbicacion(rs.getString("ubicacion"));
        c.setInstitucionId(rs.getInt("institucion_id"));
        c.setActivo(rs.getBoolean("activo"));
        c.setComisionPorcentaje(rs.getBigDecimal("comision_porcentaje"));
        c.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        return c;
    }

    // Listamos todos los congresos activos
    public List<Congreso> listarActivos() throws Exception {
        String sql = "SELECT * FROM congresos WHERE activo = TRUE ORDER BY fecha_inicio ASC, id DESC";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Congreso> out = new ArrayList<>();
            while (rs.next()) {
                out.add(map(rs));
            }
            return out;
        }
    }

    public Congreso buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM congresos WHERE id = ? AND activo = TRUE";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }
}
