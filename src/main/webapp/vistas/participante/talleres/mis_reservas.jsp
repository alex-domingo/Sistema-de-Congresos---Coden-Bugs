<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%
  List<String> mis = (List<String>) request.getAttribute("misReservas");
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Todas mis reservas</title></head>
    <body>
        <h2>Todas mis reservas</h2>
        <p>
            <a href="<%=request.getContextPath()%>/participante/dashboard">← Volver</a> |
            <a href="<%=request.getContextPath()%>/participante/talleres">Explorar talleres y ponencias</a>
        </p>

        <ul>
            <% if (mis!=null && !mis.isEmpty()) {
     for (String linea : mis) { %>
            <li><%= linea %></li>
                <% } } else { %>
            <li>No tienes reservas todavía.</li>
                <% } %>
        </ul>
    </body>
</html>
