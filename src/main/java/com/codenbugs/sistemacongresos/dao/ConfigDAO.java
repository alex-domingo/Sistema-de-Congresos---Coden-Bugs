package com.codenbugs.sistemacongresos.dao;

import com.codenbugs.sistemacongresos.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;

public class ConfigDAO {

    private final DBConnection DB;

    public ConfigDAO(DBConnection db) {
        this.DB = db;
    }

    public BigDecimal obtenerComisionPercent(Connection cn) throws Exception {
        String sql = "SELECT valor FROM configuraciones_sistema WHERE clave='commission_percent'";
        try (PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                try {
                    return new BigDecimal(rs.getString(1));
                } catch (NumberFormatException nfe) {
                    return new BigDecimal("10.00");
                }
            }
        }
        return new BigDecimal("10.00");
    }
}
