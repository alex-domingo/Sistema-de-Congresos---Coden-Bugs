<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Recargar cartera</title>
    </head>
    <body>
        <h2>Recargar cartera</h2>
        <p>
            <a href="<%=request.getContextPath()%>/participante/dashboard">← Volver</a> |
            <a href="<%=request.getContextPath()%>/participante/cartera/historial">Historial</a>
        </p>

        <%
          String ok = (String) session.getAttribute("flash_ok");
          if (ok != null) { session.removeAttribute("flash_ok"); %>
        <div style="color:green;"><%= ok %></div>
        <% } %>

        <% String error = (String) request.getAttribute("error");
   if (error != null) { %>
        <div style="color:red;"><%= error %></div>
        <% } %>

        <form method="post" action="<%=request.getContextPath()%>/participante/cartera/recargar">
            <label>Monto a recargar (GTQ) *</label><br/>
            <input type="number" name="monto" min="1" step="0.01" required />
            <br/><br/>
            <button type="submit">Recargar</button>
        </form>
    </body>
</html>
