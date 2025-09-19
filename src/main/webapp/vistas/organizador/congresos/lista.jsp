<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.codenbugs.sistemacongresos.modelos.Congreso" %>
<%
  List<Congreso> congresos = (List<Congreso>) request.getAttribute("congresos");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Mis Congresos</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/estilos.css">
    </head>
    <body>
        <h2>Mis Congresos</h2>
        <p><a href="<%=request.getContextPath()%>/organizador/dashboard">← Volver</a></p>
        <p><a href="<%=request.getContextPath()%>/organizador/congresos/crear">+ Nuevo congreso</a></p>

        <table border="1" cellpadding="6" cellspacing="0">
            <thead>
                <tr>
                    <th>ID</th><th>Título</th><th>Fechas</th><th>Precio (GTQ)</th><th>Ubicación</th><th>Activo</th><th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% if (congresos != null) { for (Congreso c : congresos) { %>
                <tr>
                    <td><%=c.getId()%></td>
                    <td><%=c.getTitulo()%></td>
                    <td><%=c.getFechaInicio()%> → <%=c.getFechaFin()%></td>
                    <td><%=c.getPrecio()%></td>
                    <td><%=c.getUbicacion()%></td>
                    <td><%=c.getActivo() ? "Sí" : "No"%></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/organizador/congresos/editar?id=<%=c.getId()%>">Editar</a>
                        |
                        <form action="<%=request.getContextPath()%>/organizador/congresos/estado" method="post" style="display:inline">
                            <input type="hidden" name="id" value="<%=c.getId()%>"/>
                            <input type="hidden" name="activo" value="<%= !c.getActivo() %>"/>
                            <button type="submit"><%= c.getActivo() ? "Desactivar" : "Activar" %></button>
                        </form>
                    </td>
                </tr>
                <% } } %>
            </tbody>
        </table>
    </body>
</html>
