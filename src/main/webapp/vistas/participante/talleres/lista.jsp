<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Actividad" %>
<%
  List<Actividad> actividades = (List<Actividad>) request.getAttribute("actividades");
  String ok = (String) session.getAttribute("flash_ok");
  String err = (String) session.getAttribute("flash_error");
  if (ok!=null) session.removeAttribute("flash_ok");
  if (err!=null) session.removeAttribute("flash_error");
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Explorar talleres y ponencias</title></head>
    <body>
        <h2>Explorar talleres y ponencias</h2>
        <p>
            <a href="<%=request.getContextPath()%>/participante/dashboard">← Volver</a> |
            <a href="<%=request.getContextPath()%>/participante/talleres/mis">Todas mis reservas</a>
        </p>

        <% if (ok!=null) { %><div style="color:green;"><%=ok%></div><% } %>
        <% if (err!=null) { %><div style="color:red;"><%=err%></div><% } %>

        <% if (actividades!=null && !actividades.isEmpty()) {
             for (Actividad a : actividades) {
               boolean esTaller = "TALLER".equals(a.getTipoActividad());
        %>
        <div style="border:1px solid #ccc; padding:10px; margin:10px;">
            <h3><%= a.getNombre() %> <small>(<%= a.getTipoActividad() %>)</small></h3>
            <p><b>Descripción:</b> <%= a.getDescripcion()!=null?a.getDescripcion():"Sin descripción" %></p>
            <p><b>Horario:</b> <%= a.getFechaHoraInicio() %> → <%= a.getFechaHoraFin() %></p>
            <p><b>Salón:</b> <%= a.getSalonId() %></p>
            <form method="post" action="<%=request.getContextPath()%>/participante/talleres/reservar">
                <input type="hidden" name="actividad_id" value="<%=a.getId()%>"/>
                <button type="submit"><%= esTaller ? "Reservar cupo" : "Agregar a mi agenda" %></button>
            </form>
        </div>
        <% } } else { %>
        <p>No hay actividades disponibles por el momento.</p>
        <% } %>
    </body>
</html>
