package com.codenbugs.sistemacongresos.controladores.salones;

import com.codenbugs.sistemacongresos.dao.SalonDAO;
import com.codenbugs.sistemacongresos.modelos.Salon;
import com.codenbugs.sistemacongresos.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SalonesPorCongresoServlet", urlPatterns = {"/organizador/salones/porCongreso"})
public class SalonesPorCongresoServlet extends HttpServlet {

    private SalonDAO salonDAO;

    @Override
    public void init() {
        salonDAO = new SalonDAO(new DBConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String cid = req.getParameter("congresoId");
        if (cid == null || cid.isBlank()) {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write("<option value=\"\">— Selecciona —</option>");
            return;
        }
        try {
            int congresoId = Integer.parseInt(cid);
            List<Salon> salones = salonDAO.listarPorCongreso(congresoId);

            resp.setContentType("text/html; charset=UTF-8");
            StringBuilder sb = new StringBuilder();
            sb.append("<option value=\"\">— Selecciona —</option>");
            for (Salon s : salones) {
                sb.append("<option value=\"").append(s.getId()).append("\">")
                        .append(s.getNombre()).append(" (ID ").append(s.getId()).append(")")
                        .append("</option>");
            }
            resp.getWriter().write(sb.toString());
        } catch (Exception e) {
            // En error, devolvemos un option “vacío”
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write("<option value=\"\">— Sin salones —</option>");
        }
    }
}
