package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.modelos.Congreso;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongresoDAO {

    private final DBConnection DB;

    public CongresoDAO(DBConnection db) {
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

    /*
    Listamos los congresos pertenecientes a instituciones donde el usuario es 
    admin (organizador)
     */
    public List<Congreso> listarPorOrganizador(int usuarioId) throws SQLException {
        String sql
                = "SELECT c.* FROM congresos c "
                + "JOIN administradores_instituciones ai ON ai.institucion_id = c.institucion_id "
                + "WHERE ai.usuario_id = ? ORDER BY c.fecha_inicio DESC, c.id DESC";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Congreso> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(map(rs));
                }
                return out;
            }
        }
    }

    public Congreso buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM congresos WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    // Verificamos que el congreso pertenezca a alguna institución del organizador
    public boolean perteneceAOrganizador(int congresoId, int usuarioId) throws SQLException {
        String sql
                = "SELECT 1 FROM congresos c "
                + "JOIN administradores_instituciones ai ON ai.institucion_id = c.institucion_id "
                + "WHERE c.id = ? AND ai.usuario_id = ? LIMIT 1";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, congresoId);
            ps.setInt(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Creamos y retornamos el id generado
    public int crear(Congreso c) throws SQLException {
        String sql = "INSERT INTO congresos "
                + "(titulo, descripcion, fecha_inicio, fecha_fin, precio, ubicacion, institucion_id, activo, comision_porcentaje) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDescripcion());
            ps.setDate(3, c.getFechaInicio());
            ps.setDate(4, c.getFechaFin());
            ps.setBigDecimal(5, c.getPrecio());
            ps.setString(6, c.getUbicacion());
            ps.setInt(7, c.getInstitucionId());
            ps.setBoolean(8, c.getActivo() != null ? c.getActivo() : true);
            ps.setBigDecimal(9, c.getComisionPorcentaje() != null ? c.getComisionPorcentaje() : new BigDecimal("10.00"));

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return 0;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            return rows;
        }
    }

    public boolean actualizar(Congreso c) throws SQLException {
        String sql = "UPDATE congresos SET titulo=?, descripcion=?, fecha_inicio=?, fecha_fin=?, "
                + "precio=?, ubicacion=?, institucion_id=?, activo=?, comision_porcentaje=? WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDescripcion());
            ps.setDate(3, c.getFechaInicio());
            ps.setDate(4, c.getFechaFin());
            ps.setBigDecimal(5, c.getPrecio());
            ps.setString(6, c.getUbicacion());
            ps.setInt(7, c.getInstitucionId());
            ps.setBoolean(8, c.getActivo() != null ? c.getActivo() : true);
            ps.setBigDecimal(9, c.getComisionPorcentaje() != null ? c.getComisionPorcentaje() : new BigDecimal("10.00"));
            ps.setInt(10, c.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean cambiarEstado(int id, boolean activo) throws SQLException {
        String sql = "UPDATE congresos SET activo=? WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }
}
