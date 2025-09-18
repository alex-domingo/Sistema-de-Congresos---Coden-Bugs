package com.codenbugs.sistemacongresos.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/auth/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion != null) {
            sesion.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/auth/login");
    }
}
