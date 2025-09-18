package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdministradorInstitucionDAO {

    private final DBConnection DB;

    public AdministradorInstitucionDAO(DBConnection db) {
        this.DB = db;
    }

    /*
    Devolvemos true si el usuario aparece como admin de alguna institución
     */
    public boolean esOrganizador(int usuarioId) throws Exception {
        String sql = "SELECT 1 FROM administradores_instituciones WHERE usuario_id = ? LIMIT 1";
        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
