<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Salon" %>
<%
  List<Salon> salones=(List<Salon>) request.getAttribute("salones");
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Salones</title></head>
    <body>
        <h2>Salones</h2>
        <p><a href="<%=request.getContextPath()%>/organizador/dashboard">← Volver</a></p>
        <p><a href="<%=request.getContextPath()%>/organizador/salones/crear">+ Nuevo salón</a></p>

        <table border="1" cellpadding="6" cellspacing="0">
            <thead><tr><th>ID</th><th>Nombre</th><th>Ubicación</th><th>Capacidad</th><th>Congreso</th><th>Acciones</th></tr></thead>
            <tbody>
                <% if (salones!=null) for (Salon s: salones) { %>
                <tr>
                    <td><%=s.getId()%></td>
                    <td><%=s.getNombre()%></td>
                    <td><%=s.getUbicacion()%></td>
                    <td><%=s.getCapacidad()!=null?s.getCapacidad():""%></td>
                    <td><%=s.getCongresoId()%></td>
                    <td><a href="<%=request.getContextPath()%>/organizador/salones/editar?id=<%=s.getId()%>">Editar</a></td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
