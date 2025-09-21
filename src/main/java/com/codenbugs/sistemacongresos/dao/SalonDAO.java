package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.modelos.Salon;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalonDAO {

    private final DBConnection DB;

    public SalonDAO(DBConnection db) {
        this.DB = db;
    }

    private Salon map(ResultSet rs) throws SQLException {
        Salon s = new Salon();
        s.setId(rs.getInt("id"));
        s.setNombre(rs.getString("nombre"));
        s.setUbicacion(rs.getString("ubicacion"));
        s.setCapacidad((Integer) rs.getObject("capacidad"));
        s.setDescripcion(rs.getString("descripcion"));
        s.setCongresoId(rs.getInt("congreso_id"));
        s.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        return s;
    }

    // Listamos solamente los salones de congresos que administra el organizador
    public List<Salon> listarPorOrganizador(int usuarioId) throws Exception {
        String sql = "SELECT s.* FROM salones s "
                + "JOIN congresos c ON c.id = s.congreso_id "
                + "JOIN administradores_instituciones ai ON ai.institucion_id = c.institucion_id "
                + "WHERE ai.usuario_id = ? ORDER BY s.id DESC";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Salon> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(map(rs));
                }
                return out;
            }
        }
    }

    public List<Salon> listarPorCongreso(int congresoId) throws Exception {
        String sql = "SELECT * FROM salones WHERE congreso_id=? ORDER BY nombre";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, congresoId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Salon> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(map(rs));
                }
                return out;
            }
        }
    }

    public boolean perteneceAOrganizador(int salonId, int usuarioId) throws Exception {
        String sql = "SELECT 1 FROM salones s "
                + "JOIN congresos c ON c.id = s.congreso_id "
                + "JOIN administradores_instituciones ai ON ai.institucion_id = c.institucion_id "
                + "WHERE s.id=? AND ai.usuario_id=? LIMIT 1";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, salonId);
            ps.setInt(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int crear(Salon s) throws Exception {
        String sql = "INSERT INTO salones (nombre, ubicacion, capacidad, descripcion, congreso_id) VALUES (?,?,?,?,?)";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getUbicacion());
            if (s.getCapacidad() != null) {
                ps.setInt(3, s.getCapacidad());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, s.getDescripcion());
            ps.setInt(5, s.getCongresoId());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return 0;
            }
            try (ResultSet k = ps.getGeneratedKeys()) {
                return k.next() ? k.getInt(1) : rows;
            }
        }
    }

    public boolean actualizar(Salon s) throws Exception {
        String sql = "UPDATE salones SET nombre=?, ubicacion=?, capacidad=?, descripcion=?, congreso_id=? WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getUbicacion());
            if (s.getCapacidad() != null) {
                ps.setInt(3, s.getCapacidad());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, s.getDescripcion());
            ps.setInt(5, s.getCongresoId());
            ps.setInt(6, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public Salon buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM salones WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }
}
