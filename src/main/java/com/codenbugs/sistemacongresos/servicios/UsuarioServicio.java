package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.AdministradorInstitucionDAO;
import com.codenbugs.sistemacongresos.dao.UsuarioDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;

public class UsuarioServicio {

    private final UsuarioDAO USUARIO_DAO;
    private final AdministradorInstitucionDAO ADMIN_INST_DAO;

    public UsuarioServicio(UsuarioDAO usuarioDAO, AdministradorInstitucionDAO adminInstDAO) {
        this.USUARIO_DAO = usuarioDAO;
        this.ADMIN_INST_DAO = adminInstDAO;
    }

    public Usuario login(String email, String passwordPlano) throws Exception {
        if (email == null || email.isBlank() || passwordPlano == null) {
            return null;
        }
        Usuario u = USUARIO_DAO.buscarPorEmail(email.trim());
        if (u == null || !u.isActivo()) {
            return null;
        }
        // Reemplazamos por hashing (Base64/BCrypt/Argon2) más adelante
        if (!passwordPlano.equals(u.getPassword())) {
            return null;
        }
        return u;
    }

    public boolean esAdminSistema(Usuario u) {
        return u != null && u.isEsAdministradorSistema();
    }

    public boolean esOrganizador(Usuario u) throws Exception {
        return u != null && ADMIN_INST_DAO.esOrganizador(u.getId());
    }

    // Devolvemos la ruta de inicio según el rol
    public String homePath(Usuario u) throws Exception {
        if (esAdminSistema(u)) {
            return "/admin/dashboard";
        }
        if (esOrganizador(u)) {
            return "/organizador/dashboard";
        }
        return "/participante/dashboard";
    }
}
