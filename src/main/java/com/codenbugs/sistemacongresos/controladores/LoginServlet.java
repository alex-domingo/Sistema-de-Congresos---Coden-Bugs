package com.codenbugs.sistemacongresos.controladores;

import com.codenbugs.sistemacongresos.dao.UsuarioDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.UsuarioServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/auth/login"})
public class LoginServlet extends HttpServlet {

    private UsuarioServicio usuarioServicio;

    @Override
    public void init() throws ServletException {
        DBConnection db = new DBConnection();
        UsuarioDAO usuarioDAO = new UsuarioDAO(db);
        this.usuarioServicio = new UsuarioServicio(usuarioDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Mostramos el formulario de login
        req.getRequestDispatcher("/vistas/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            Usuario u = usuarioServicio.login(email, password);
            if (u == null) {
                req.setAttribute("error", "Credenciales inválidas o usuario inactivo.");
                req.getRequestDispatcher("/vistas/auth/login.jsp").forward(req, resp);
                return;
            }

            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuario", u);

            // Redirección por rol (admin sistema vs organizador vs participante)
            if (u.isEsAdministradorSistema()) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                /*
                Aquí podremos decidir si el usuario es organizador (admin de institución) o participante,
                de momento, si no es admin del sistema, lo enviamos a "organizador"
                 */
                resp.sendRedirect(req.getContextPath() + "/organizador/dashboard");
            }
        } catch (Exception e) {
            throw new ServletException("Error durante login", e);
        }
    }
}
