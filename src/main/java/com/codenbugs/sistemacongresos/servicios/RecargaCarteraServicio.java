package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.TransaccionCarteraDAO;
import com.codenbugs.sistemacongresos.dao.UsuarioDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;

public class RecargaCarteraServicio {

    private final DBConnection DB;
    private final UsuarioDAO USUARIO_DAO;
    private final TransaccionCarteraDAO TXN_DAO;

    public RecargaCarteraServicio(DBConnection db) {
        this.DB = db;
        this.USUARIO_DAO = new UsuarioDAO(db);
        this.TXN_DAO = new TransaccionCarteraDAO();
    }

    // Validamos monto, suma saldo, registra transacción RECARGA
    public void recargar(int userId, BigDecimal monto) throws Exception {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0.");
        }
        // Ponemos un límite básico para evitar errores de dedo, esto puede variar
        if (monto.compareTo(new BigDecimal("10000")) > 0) {
            throw new IllegalArgumentException("El monto máximo por recarga es Q10,000.");
        }

        try (Connection cn = DB.getConnection()) {
            cn.setAutoCommit(false);
            try {
                Usuario u = USUARIO_DAO.buscarPorId(userId);
                if (u == null || !u.isActivo()) {
                    throw new IllegalArgumentException("Usuario no válido o inactivo.");
                }

                BigDecimal nuevoSaldo = u.getSaldo().add(monto);
                if (!USUARIO_DAO.actualizarSaldo(userId, nuevoSaldo)) {
                    throw new IllegalStateException("No se pudo actualizar el saldo.");
                }

                TXN_DAO.registrar(cn, userId, monto, "RECARGA",
                        "Recarga de cartera", "TOPUP-" + System.currentTimeMillis());

                cn.commit();
            } catch (Exception ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }
}
