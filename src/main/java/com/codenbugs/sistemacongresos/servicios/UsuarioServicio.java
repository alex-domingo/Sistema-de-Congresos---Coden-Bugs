package com.codenbugs.sistemacongresos.servicios;

import com.codenbugs.sistemacongresos.dao.AdministradorInstitucionDAO;
import com.codenbugs.sistemacongresos.dao.UsuarioDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.util.PasswordUtil;

import java.math.BigDecimal;

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
        // Comparamos contra hash sha256Base64
        String hashIngresado = PasswordUtil.sha256Base64(passwordPlano);
        if (!hashIngresado.equals(u.getPassword())) {
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

    // Registramos el participante
    public int registrarParticipante(String nombreCompleto,
            String organizacion,
            String email,
            String telefono,
            String identificacion,
            byte[] foto,
            String passwordPlano) throws Exception {

        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }

        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Correo electrónico inválido.");
        }

        if (passwordPlano == null || passwordPlano.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }

        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación personal es obligatoria.");
        }

        // Manejamos que no se puedan registrar con el mismo correo eléctronico
        if (USUARIO_DAO.buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con ese correo.");
        }

        // Armamos modelo
        Usuario u = new Usuario();
        u.setNombreCompleto(nombreCompleto.trim());
        u.setOrganizacion(organizacion != null ? organizacion.trim() : null);
        u.setEmail(email.trim().toLowerCase());
        u.setTelefono(telefono != null ? telefono.trim() : null);
        u.setIdentificacion(identificacion.trim());
        u.setFoto(foto);
        u.setPassword(PasswordUtil.sha256Base64(passwordPlano));
        u.setActivo(true);
        u.setEsAdministradorSistema(false); // Siempre participante
        u.setSaldo(BigDecimal.ZERO);

        return USUARIO_DAO.crear(u);
    }
}
