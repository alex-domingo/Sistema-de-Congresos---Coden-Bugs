<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.codenbugs.sistemacongresos.modelos.Usuario" %>
<%
  Usuario u = (Usuario) session.getAttribute("usuario");
  String nombre = (u!=null && u.getNombreCompleto()!=null) ? u.getNombreCompleto() : "Usuario";
  String email = (u!=null) ? u.getEmail() : "";
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Organizador – Dashboard</title></head>
    <body>
        <h2>Dashboard Organizador</h2>
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
            <li><a href="<%=request.getContextPath()%>/organizador/congresos">Mis Congresos</a></li>
            <li><a href="<%=request.getContextPath()%>/organizador/salones">Salones</a></li>
            <li><a href="<%=request.getContextPath()%>/organizador/actividades">Actividades</a></li>
            <li><a href="#">Convocatorias & Trabajos</a></li>
            <li><a href="#">Reportes del Congreso</a></li>
        </ul>
    </body>
</html>
