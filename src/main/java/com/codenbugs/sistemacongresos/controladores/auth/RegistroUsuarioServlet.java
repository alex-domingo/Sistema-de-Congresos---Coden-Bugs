package com.codenbugs.sistemacongresos.controladores.auth;

import com.codenbugs.sistemacongresos.dao.AdministradorInstitucionDAO;
import com.codenbugs.sistemacongresos.dao.UsuarioDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.UsuarioServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import com.codenbugs.sistemacongresos.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "RegistroUsuarioServlet", urlPatterns = {"/auth/registro"})
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5 MB para foto
public class RegistroUsuarioServlet extends HttpServlet {

    private UsuarioServicio usuarioServicio;

    @Override
    public void init() {
        DBConnection db = new DBConnection();
        this.usuarioServicio = new UsuarioServicio(new UsuarioDAO(db), new AdministradorInstitucionDAO(db));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/vistas/auth/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String nombre = req.getParameter("nombre_completo");
        String organizacion = req.getParameter("organizacion");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        String identificacion = req.getParameter("identificacion");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        byte[] foto = null;
        try {
            Part part = req.getPart("foto");
            if (part != null && part.getSize() > 0) {
                try (InputStream in = part.getInputStream()) {
                    foto = in.readAllBytes();
                }
            }
        } catch (Exception ignored) {
            // Si falla la lectura de foto no bloqueamos el registro
        }

        try {
            if (password == null || !password.equals(password2)) {
                throw new IllegalArgumentException("Las contraseñas no coinciden.");
            }
            if (password.length() < 6) {
                throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
            }

            // Llamamos al registro (el servicio hará validaciones de email único, etc.)
            int nuevoId = usuarioServicio.registrarParticipante(
                    nombre, organizacion, email, telefono, identificacion, foto,
                    /*
                    Guardaremos Base64(SHA-256) en DB y el servicio
                    debe invocar internamente PasswordUtil.sha256Base64(...)
                     */
                    password
            );

            // Dejamos en sesión un Usuario "ligero", un autologin
            Usuario u = new Usuario();
            u.setId(nuevoId);
            u.setNombreCompleto(nombre);
            u.setEmail(email != null ? email.toLowerCase() : null);
            u.setActivo(true);
            u.setEsAdministradorSistema(false);
            req.getSession().setAttribute("usuario", u);

            resp.sendRedirect(req.getContextPath() + "/participante/dashboard");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            // Reinyectamos valores para no perder lo que ya escribió el usuario
            req.getRequestDispatcher("/vistas/auth/registro.jsp").forward(req, resp);
        }
    }
}
