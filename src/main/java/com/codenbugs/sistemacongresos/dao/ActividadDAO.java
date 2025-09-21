package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.modelos.Actividad;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO {

    private final DBConnection DB;

    public ActividadDAO(DBConnection db) {
        this.DB = db;
    }

    private Actividad map(ResultSet rs) throws SQLException {
        Actividad a = new Actividad();
        a.setId(rs.getInt("id"));
        a.setNombre(rs.getString("nombre"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setTipoActividad(rs.getString("tipo_actividad"));
        a.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio"));
        a.setFechaHoraFin(rs.getTimestamp("fecha_hora_fin"));
        a.setSalonId(rs.getInt("salon_id"));
        a.setCongresoId(rs.getInt("congreso_id"));
        a.setCupoMaximo((Integer) rs.getObject("cupo_maximo"));
        a.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        return a;
    }

    public List<Actividad> listarPorCongreso(int congresoId) throws Exception {
        String sql = "SELECT * FROM actividades WHERE congreso_id=? ORDER BY fecha_hora_inicio";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, congresoId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Actividad> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(map(rs));
                }
                return out;
            }
        }
    }

    public boolean perteneceAOrganizador(int actividadId, int usuarioId) throws Exception {
        String sql = "SELECT 1 FROM actividades a "
                + "JOIN congresos c ON c.id = a.congreso_id "
                + "JOIN administradores_instituciones ai ON ai.institucion_id = c.institucion_id "
                + "WHERE a.id=? AND ai.usuario_id=? LIMIT 1";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, actividadId);
            ps.setInt(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean salonYCongresoConsistentes(int salonId, int congresoId) throws Exception {
        String sql = "SELECT 1 FROM salones WHERE id=? AND congreso_id=? LIMIT 1";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, salonId);
            ps.setInt(2, congresoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /*
    Si se traslapa con otra actividad en el mismo salón (excluye idActual si
    se edita)
     */
    public boolean existeTraslapeEnSalon(int salonId, Timestamp ini, Timestamp fin, Integer idActual) throws Exception {
        String base = "SELECT 1 FROM actividades WHERE salon_id=? AND "
                + "NOT (fecha_hora_fin <= ? OR fecha_hora_inicio >= ?)";
        String sql = (idActual != null) ? base + " AND id <> ? LIMIT 1" : base + " LIMIT 1";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, salonId);
            ps.setTimestamp(2, ini);
            ps.setTimestamp(3, fin);
            if (idActual != null) {
                ps.setInt(4, idActual);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int crear(Actividad a) throws Exception {
        String sql = "INSERT INTO actividades "
                + "(nombre, descripcion, tipo_actividad, fecha_hora_inicio, fecha_hora_fin, salon_id, congreso_id, cupo_maximo) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getTipoActividad());
            ps.setTimestamp(4, a.getFechaHoraInicio());
            ps.setTimestamp(5, a.getFechaHoraFin());
            ps.setInt(6, a.getSalonId());
            ps.setInt(7, a.getCongresoId());
            if (a.getCupoMaximo() != null) {
                ps.setInt(8, a.getCupoMaximo());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return 0;
            }
            try (ResultSet k = ps.getGeneratedKeys()) {
                return k.next() ? k.getInt(1) : rows;
            }
        }
    }

    public boolean actualizar(Actividad a) throws Exception {
        String sql = "UPDATE actividades SET nombre=?, descripcion=?, tipo_actividad=?, "
                + "fecha_hora_inicio=?, fecha_hora_fin=?, salon_id=?, congreso_id=?, cupo_maximo=? WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getTipoActividad());
            ps.setTimestamp(4, a.getFechaHoraInicio());
            ps.setTimestamp(5, a.getFechaHoraFin());
            ps.setInt(6, a.getSalonId());
            ps.setInt(7, a.getCongresoId());
            if (a.getCupoMaximo() != null) {
                ps.setInt(8, a.getCupoMaximo());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.setInt(9, a.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public Actividad buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM actividades WHERE id=?";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }
}
