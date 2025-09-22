package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.modelos.Actividad;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadPublicaDAO {

    private final DBConnection DB;

    public ActividadPublicaDAO(DBConnection db) {
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

    /*
    Listamos todas las actividades (ponencias y talleres) de congresos activos
     */
    public List<Actividad> listarActividadesDisponibles() throws Exception {
        String sql = "SELECT a.* FROM actividades a "
                + "JOIN congresos c ON c.id = a.congreso_id "
                + "WHERE c.activo = TRUE "
                + "ORDER BY a.fecha_hora_inicio ASC";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Actividad> out = new ArrayList<>();
            while (rs.next()) {
                out.add(map(rs));
            }
            return out;
        }
    }

    /*
    Obtenemos una actividad (sea taller o ponencia) si su congreso está activo
     */
    public Actividad buscarActividadActivaPorId(int actividadId) throws Exception {
        String sql = "SELECT a.* FROM actividades a "
                + "JOIN congresos c ON c.id = a.congreso_id "
                + "WHERE a.id=? AND c.activo=TRUE";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, actividadId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }
}
