<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.codenbugs.sistemacongresos.modelos.Usuario" %>
<%
  Usuario u = (Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Participante – Dashboard</title></head>
    <body>
        <h2>Dashboard Participante</h2>
        <p>Hola, <b><%= (u != null ? u.getNombreCompleto() : "Usuario") %></b></p>
        <p><a href="<%=request.getContextPath()%>/auth/logout">Salir</a></p>

        <ul>
            <li><a href="#">Explorar Congresos</a></li>
            <li><a href="#">Mis Inscripciones</a></li>
            <li><a href="#">Mis Reservas de Taller</a></li>
            <li><a href="#">Mis Diplomas</a></li>
        </ul>
    </body>
</html>

