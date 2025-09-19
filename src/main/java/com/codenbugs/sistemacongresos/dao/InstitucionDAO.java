package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.modelos.Institucion;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstitucionDAO {

    private final DBConnection DB;

    public InstitucionDAO(DBConnection db) {
        this.DB = db;
    }

    private Institucion map(ResultSet rs) throws SQLException {
        Institucion i = new Institucion();
        i.setId(rs.getInt("id"));
        i.setNombre(rs.getString("nombre"));
        i.setDescripcion(rs.getString("descripcion"));
        i.setActiva(rs.getBoolean("activa"));
        i.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        return i;
    }

    public List<Institucion> listar() throws SQLException {
        String sql = "SELECT id, nombre, descripcion, activa, fecha_creacion FROM instituciones ORDER BY nombre";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Institucion> out = new ArrayList<>();
            while (rs.next()) {
                out.add(map(rs));
            }
            return out;
        }
    }

    public Institucion buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, activa, fecha_creacion FROM instituciones WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public int crear(Institucion i) throws SQLException {
        String sql = "INSERT INTO instituciones (nombre, descripcion, activa) VALUES (?,?,?)";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getDescripcion());
            ps.setBoolean(3, i.getActiva() != null ? i.getActiva() : true);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return 0;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    i.setId(keys.getInt(1));
                    return i.getId();
                }
            }
            return rows;
        }
    }

    public boolean actualizar(Institucion i) throws SQLException {
        String sql = "UPDATE instituciones SET nombre=?, descripcion=?, activa=? WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getDescripcion());
            ps.setBoolean(3, i.getActiva() != null ? i.getActiva() : true);
            ps.setInt(4, i.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean cambiarEstado(int id, boolean activa) throws SQLException {
        String sql = "UPDATE instituciones SET activa=? WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBoolean(1, activa);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }
}
