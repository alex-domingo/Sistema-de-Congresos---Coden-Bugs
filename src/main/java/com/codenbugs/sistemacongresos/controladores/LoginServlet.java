package com.codenbugs.sistemacongresos.controladores;

import com.codenbugs.sistemacongresos.dao.AdministradorInstitucionDAO;
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
        AdministradorInstitucionDAO adminInstDAO = new AdministradorInstitucionDAO(db);
        this.usuarioServicio = new UsuarioServicio(usuarioDAO, adminInstDAO);
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

            String destino = usuarioServicio.homePath(u); // admin, organizador o participante
            resp.sendRedirect(req.getContextPath() + destino);
        } catch (Exception e) {
            throw new ServletException("Error durante login", e);
        }
    }
}
