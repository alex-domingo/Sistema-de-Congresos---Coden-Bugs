<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.codenbugs.sistemacongresos.modelos.Congreso" %>
<%
  List<Congreso> congresos = (List<Congreso>) request.getAttribute("congresos");
  String ok = (String) session.getAttribute("flash_ok");
  String err = (String) session.getAttribute("flash_error");
  if (ok != null) session.removeAttribute("flash_ok");
  if (err != null) session.removeAttribute("flash_error");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Explorar Congresos</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/estilos.css">
    </head>
    <body>
        <h2>Explorar Congresos</h2>
        <p><a href="<%=request.getContextPath()%>/participante/dashboard">← Volver</a></p>

        <% if (ok != null) { %><div style="color:green;"><%= ok %></div><% } %>
        <% if (err != null) { %><div style="color:red;"><%= err %></div><% } %>

        <table border="1" cellpadding="6" cellspacing="0">
            <thead>
                <tr>
                    <th>Título</th><th>Fechas</th><th>Precio (GTQ)</th><th>Ubicación</th><th>Acción</th>
                </tr>
            </thead>
            <tbody>
                <% if (congresos != null) {
       for (Congreso c : congresos) { %>
                <tr>
                    <td><%= c.getTitulo() %></td>
                    <td><%= c.getFechaInicio() %> → <%= c.getFechaFin() %></td>
                    <td><%= c.getPrecio() %></td>
                    <td><%= c.getUbicacion() %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/participante/congresos/inscribir" style="display:inline">
                            <input type="hidden" name="congreso_id" value="<%= c.getId() %>" />
                            <button type="submit">Inscribirme</button>
                        </form>
                    </td>
                </tr>
                <% } } %>
            </tbody>
        </table>
    </body>
</html>
