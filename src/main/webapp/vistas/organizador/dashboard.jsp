<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.codenbugs.sistemacongresos.modelos.Usuario" %>
<%
  Usuario u = (Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Organizador – Dashboard</title></head>
    <body>
        <h2>Dashboard Organizador</h2>
        <p>Hola, <b><%= (u != null ? u.getNombreCompleto() : "Usuario") %></b></p>
        <p><a href="<%=request.getContextPath()%>/auth/logout">Salir</a></p>

        <ul>
            <li><a href="<%=request.getContextPath()%>/organizador/congresos">Mis Congresos</a></li>
            <li><a href="#">Salones & Actividades</a></li>
            <li><a href="#">Convocatorias & Trabajos</a></li>
            <li><a href="#">Reportes del Congreso</a></li>
        </ul>
    </body>
</html>

