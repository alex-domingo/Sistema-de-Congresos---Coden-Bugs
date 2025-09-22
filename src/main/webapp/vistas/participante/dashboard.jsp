<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.codenbugs.sistemacongresos.modelos.Usuario" %>
<%
  Usuario u = (Usuario) session.getAttribute("usuario");
  String nombre = (u!=null && u.getNombreCompleto()!=null) ? u.getNombreCompleto() : "Usuario";
  String email = (u!=null) ? u.getEmail() : "";
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Participante – Dashboard</title></head>
    <body>
        <h2>Dashboard Participante</h2>
        <p>Hola, <b><%= (u != null ? u.getNombreCompleto() : "Usuario") %></b></p>

        <div style="display:flex; align-items:center; gap:12px; margin-bottom:16px;">
            <img
                src="<%=request.getContextPath()%>/usuario/foto?v=<%=System.currentTimeMillis()%>"
                alt="Mi foto"
                width="80" height="80"
                style="border-radius:50%; object-fit:cover; border:1px solid #ddd;"
                />
            <div>
                <div style="font-weight:bold;"><%= nombre %></div>
                <div style="font-size:.9em; color:#555;"><%= email %></div>
            </div>
        </div>

        <p><a href="<%=request.getContextPath()%>/auth/logout">Salir</a></p>

        <ul>
            <li><a href="<%=request.getContextPath()%>/participante/congresos">Explorar Congresos</a></li>
            <li><a href="<%=request.getContextPath()%>/participante/talleres">Explorar talleres y ponencias</a></li>
            <li><a href="<%=request.getContextPath()%>/participante/talleres/mis">Todas mis reservas</a></li>
            <li><a href="<%=request.getContextPath()%>/participante/cartera/recargar">Recargar cartera</a></li>
            <li><a href="#">Mis Diplomas</a></li>
        </ul>

    </body>
</html>
