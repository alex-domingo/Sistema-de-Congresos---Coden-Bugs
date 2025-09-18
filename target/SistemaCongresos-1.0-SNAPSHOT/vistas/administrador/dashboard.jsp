<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.codenbugs.sistemacongresos.modelos.Usuario" %>
<%
  Usuario u = (Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Admin – Dashboard</title></head>
    <body>
        <h2>Dashboard Admin Sistema</h2>
        <p>Hola, <b><%= (u != null ? u.getNombreCompleto() : "Usuario") %></b></p>
        <p><a href="<%=request.getContextPath()%>/auth/logout">Salir</a></p>

        <ul>
            <li><a href="#">Instituciones</a></li>
            <li><a href="#">Usuarios</a></li>
            <li><a href="#">Configuración (Comisión)</a></li>
            <li><a href="#">Reportes</a></li>
        </ul>
    </body>
</html>
