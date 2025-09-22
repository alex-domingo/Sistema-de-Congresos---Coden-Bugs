package com.codenbugs.sistemacongresos.controladores.participante;

import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.servicios.RecargaCarteraServicio;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "RecargarCarteraServlet", urlPatterns = {"/participante/cartera/recargar"})
public class RecargarCarteraServlet extends HttpServlet {

    private RecargaCarteraServicio servicio;

    @Override
    public void init() {
        servicio = new RecargaCarteraServicio(new DBConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        req.getRequestDispatcher("/vistas/participante/cartera/recargar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String montoStr = req.getParameter("monto");
        try {
            if (montoStr == null || montoStr.isBlank()) {
                throw new IllegalArgumentException("Ingresa un monto.");
            }
            BigDecimal monto = new BigDecimal(montoStr);

            servicio.recargar(u.getId(), monto);
            req.getSession().setAttribute("flash_ok", "Recarga realizada por Q" + monto.toPlainString());
            resp.sendRedirect(req.getContextPath() + "/participante/cartera/recargar");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/vistas/participante/cartera/recargar.jsp").forward(req, resp);
        }
    }
}
