package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final DBConnection DB;

    public UsuarioDAO(DBConnection db) {
        this.DB = db;
    }

    // Mapeamos un ResultSet a Usuario
    private Usuario map(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombreCompleto(rs.getString("nombre_completo"));
        u.setOrganizacion(rs.getString("organizacion"));
        u.setEmail(rs.getString("email"));
        u.setTelefono(rs.getString("telefono"));
        u.setIdentificacion(rs.getString("identificacion"));
        u.setFoto(rs.getBytes("foto")); // Puede ser null
        u.setPassword(rs.getString("password")); // No la debemos exponer
        u.setActivo(rs.getBoolean("activo"));
        u.setEsAdministradorSistema(rs.getBoolean("es_administrador_sistema"));

        BigDecimal saldo = rs.getBigDecimal("saldo");
        u.setSaldo(saldo != null ? saldo : BigDecimal.ZERO);

        u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return u;
    }

    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre_completo, organizacion, email, telefono, identificacion, "
                + "foto, password, activo, es_administrador_sistema, saldo, fecha_registro "
                + "FROM usuarios ORDER BY id";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Usuario> list = new ArrayList<>();
            while (rs.next()) {
                list.add(map(rs));
            }
            return list;
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre_completo, organizacion, email, telefono, identificacion, "
                + "foto, password, activo, es_administrador_sistema, saldo, fecha_registro "
                + "FROM usuarios WHERE id = ?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        }
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT id, nombre_completo, organizacion, email, telefono, identificacion, "
                + "foto, password, activo, es_administrador_sistema, saldo, fecha_registro "
                + "FROM usuarios WHERE email = ?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        }
    }

    public int crear(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios "
                + "(nombre_completo, organizacion, email, telefono, identificacion, foto, password, "
                + " activo, es_administrador_sistema, saldo) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getOrganizacion());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getIdentificacion());
            ps.setBytes(6, u.getFoto());
            ps.setString(7, u.getPassword());
            ps.setBoolean(8, u.isActivo());
            ps.setBoolean(9, u.isEsAdministradorSistema());
            ps.setBigDecimal(10, u.getSaldo() != null ? u.getSaldo() : BigDecimal.ZERO);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return 0;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    u.setId(keys.getInt(1));
                    return u.getId();
                }
            }
            return rows;
        }
    }

    public boolean actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET "
                + "nombre_completo=?, organizacion=?, email=?, telefono=?, identificacion=?, "
                + "foto=?, password=?, activo=?, es_administrador_sistema=?, saldo=? "
                + "WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getOrganizacion());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getIdentificacion());
            ps.setBytes(6, u.getFoto());
            ps.setString(7, u.getPassword());
            ps.setBoolean(8, u.isActivo());
            ps.setBoolean(9, u.isEsAdministradorSistema());
            ps.setBigDecimal(10, u.getSaldo() != null ? u.getSaldo() : BigDecimal.ZERO);
            ps.setInt(11, u.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // Desactivamos lógicamente al usuario (utilizamos UPDATE en lugar de DELETE)
    public boolean desactivar(int id) throws SQLException {
        String sql = "UPDATE usuarios SET activo = FALSE WHERE id = ?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizarSaldo(int id, BigDecimal nuevoSaldo) throws SQLException {
        String sql = "UPDATE usuarios SET saldo = ? WHERE id = ?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBigDecimal(1, nuevoSaldo);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }
}
