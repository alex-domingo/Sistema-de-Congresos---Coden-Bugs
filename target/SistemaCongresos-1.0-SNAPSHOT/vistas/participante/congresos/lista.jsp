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
            <tbody>
                <% if (congresos != null) {
     for (Congreso c : congresos) { %>
            <div style="border:1px solid #ccc; padding:10px; margin:10px;">
                <h3><%= c.getTitulo() %></h3>
                <p><b>Descripción:</b> <%= c.getDescripcion()!=null ? c.getDescripcion() : "Sin descripción" %></p>
                <p><b>Fechas:</b> <%= c.getFechaInicio() %> → <%= c.getFechaFin() %></p>
                <p><b>Precio:</b> Q<%= c.getPrecio() %></p>
                <p><b>Ubicación:</b> <%= c.getUbicacion() %></p>
                <form method="post" action="<%=request.getContextPath()%>/participante/congresos/inscribir">
                    <input type="hidden" name="congreso_id" value="<%= c.getId() %>" />
                    <button type="submit">Inscribirme</button>
                </form>
            </div>
            <% } } %>
        </tbody>
    </table>
</body>
</html>
