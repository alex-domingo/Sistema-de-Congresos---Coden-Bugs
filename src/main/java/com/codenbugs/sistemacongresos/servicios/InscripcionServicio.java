package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.*;
import com.codenbugs.sistemacongresos.modelos.Congreso;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;

public class InscripcionServicio {

    private final DBConnection DB;
    private final CongresoPublicoDAO CONGRESO_DAO;
    private final ParticipacionDAO PARTICIPACION_DAO;
    private final UsuarioDAO USUARIO_DAO;
    private final TransaccionCarteraDAO TXN_DAO;
    private final ConfigDAO CONFIG_DAO;

    public InscripcionServicio(DBConnection db) {
        this.DB = db;
        this.CONGRESO_DAO = new CongresoPublicoDAO(db);
        this.PARTICIPACION_DAO = new ParticipacionDAO(db);
        this.USUARIO_DAO = new UsuarioDAO(db);
        this.TXN_DAO = new TransaccionCarteraDAO();
        this.CONFIG_DAO = new ConfigDAO(db);
    }

    /*
    Intentamos inscribir al usuario en el congreso, verificamos si el congreso 
    está activo, no duplicado, saldo suficiente y que descuente saldo, insertar 
    inscripcion pagada & registra transacción negativa
     */
    public void inscribir(int userId, int congresoId) throws Exception {
        try (Connection cn = DB.getConnection()) {
            cn.setAutoCommit(false);
            try {
                // 1) Validaciones de congreso
                Congreso c = CONGRESO_DAO.buscarPorId(congresoId);
                if (c == null || (c.getActivo() != null && !c.getActivo())) {
                    throw new IllegalArgumentException("Congreso no disponible.");
                }

                // 2) Ya inscrito
                if (PARTICIPACION_DAO.yaInscrito(cn, userId, congresoId)) {
                    throw new IllegalArgumentException("Ya estás inscrito en este congreso.");
                }

                // 3) Saldo suficiente
                var usuario = USUARIO_DAO.buscarPorId(userId);
                if (usuario == null || !usuario.isActivo()) {
                    throw new IllegalArgumentException("Usuario no válido o inactivo.");
                }

                BigDecimal precio = c.getPrecio();
                if (precio == null) {
                    throw new IllegalArgumentException("Precio del congreso inválido.");
                }
                if (usuario.getSaldo().compareTo(precio) < 0) {
                    throw new IllegalArgumentException("Saldo insuficiente. Recarga tu cartera.");
                }

                // 4) Descuento de saldo
                BigDecimal nuevoSaldo = usuario.getSaldo().subtract(precio);
                if (!USUARIO_DAO.actualizarSaldo(userId, nuevoSaldo)) {
                    throw new IllegalStateException("No se pudo actualizar el saldo.");
                }

                // 5) Insertar inscripción pagada
                int inscId = PARTICIPACION_DAO.insertarInscripcion(cn, userId, congresoId, precio);
                if (inscId == 0) {
                    throw new IllegalStateException("No se pudo registrar la inscripción.");
                }

                // 6) Transacción de cartera (negativa)
                TXN_DAO.registrar(cn, userId, precio.negate(), "PAGO_INSCRIPCION",
                        "Pago inscripción: " + c.getTitulo(),
                        "PAY-" + congresoId + "-" + userId);
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
