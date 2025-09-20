package com.codenbugs.sistemacongresos.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransaccionCarteraDAO {

    // Registramos un movimiento en transacciones_cartera
    public void registrar(Connection cn, int userId, BigDecimal monto, String tipo, String descripcion, String ref) throws Exception {
        String sql = "INSERT INTO transacciones_cartera (usuario_id, monto, tipo_transaccion, fecha_transaccion, descripcion, referencia) "
                + "VALUES (?, ?, ?, NOW(), ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setBigDecimal(2, monto);
            ps.setString(3, tipo);
            ps.setString(4, descripcion);
            ps.setString(5, ref);
            ps.executeUpdate();
        }
    }
}
