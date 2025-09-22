package com.codenbugs.sistemacongresos.controladores.usuario;

import com.codenbugs.sistemacongresos.dao.UsuarioDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "UsuarioFotoServlet", urlPatterns = {"/usuario/foto"})
public class UsuarioFotoServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO(new DBConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Id opcional por query param, si no viene, usamos el de la sesión
        Integer userId = null;
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isBlank()) {
            try {
                userId = Integer.valueOf(idParam);
            } catch (NumberFormatException ignored) {
            }
        }
        if (userId == null) {
            var u = (Usuario) req.getSession().getAttribute("usuario");
            if (u != null) {
                userId = u.getId();
            }
        }
        if (userId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuario no especificado");
            return;
        }

        // Obtenemos usuario y su foto
        try {
            Usuario u = usuarioDAO.buscarPorId(userId);
            byte[] foto = (u != null) ? u.getFoto() : null;

            if (foto == null || foto.length == 0) {
                // Si no agrega foto servimos avatar por defecto desde /recursos/imagenes/avatar.png
                // Redirigimos para aprovechar el estático del contenedor
                String ctx = req.getContextPath();
                resp.sendRedirect(ctx + "/recursos/imagenes/avatar.png");
                return;
            }

            // Detección simple de (PNG/JPEG); si falla, usamos image/jpeg
            String contentType = guessImageContentType(foto);
            resp.setContentType(contentType);
            resp.setHeader("Cache-Control", "public, max-age=3600");

            resp.setContentLength(foto.length);
            try (OutputStream out = resp.getOutputStream()) {
                out.write(foto);
            }
        } catch (Exception e) {
            throw new ServletException("No se pudo cargar la foto de usuario", e);
        }
    }

    private String guessImageContentType(byte[] data) {
        if (data.length >= 8
                && (data[0] & 0xFF) == 0x89 && data[1] == 0x50 && data[2] == 0x4E && data[3] == 0x47) {
            return "image/png";
        }
        if (data.length >= 3
                && (data[0] & 0xFF) == 0xFF && (data[1] & 0xFF) == 0xD8 && (data[2] & 0xFF) == 0xFF) {
            return "image/jpeg";
        }
        return "image/jpeg";
    }
}
