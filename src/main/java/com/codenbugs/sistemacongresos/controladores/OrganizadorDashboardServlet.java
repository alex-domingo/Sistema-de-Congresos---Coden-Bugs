package com.codenbugs.sistemacongresos.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "OrganizadorDashboardServlet", urlPatterns = {"/organizador/dashboard"})
public class OrganizadorDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/vistas/organizador/dashboard.jsp").forward(req, resp);
    }
}
