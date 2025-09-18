package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.UsuarioDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;

public class UsuarioServicio {

    private final UsuarioDAO USUARIO_DAO;

    public UsuarioServicio(UsuarioDAO usuarioDAO) {
        this.USUARIO_DAO = usuarioDAO;
    }

    //Retornamos Usuario si las credenciales son válidas; null en caso contrario
    public Usuario login(String email, String passwordPlano) throws Exception {
        if (email == null || email.isBlank() || passwordPlano == null) {
            return null;
        }
        Usuario u = USUARIO_DAO.buscarPorEmail(email.trim());
        if (u == null) {
            return null;
        }
        if (!u.isActivo()) {
            return null;
        }

        // Reemplazamos por hashing (Base64/BCrypt/Argon2) más adelante
        if (passwordPlano.equals(u.getPassword())) {
            return u;
        }
        return null;
    }
}
