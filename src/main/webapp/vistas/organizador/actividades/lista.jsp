<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Actividad,com.codenbugs.sistemacongresos.modelos.Congreso" %>
<%
  List<Actividad> actividades=(List<Actividad>) request.getAttribute("actividades");
  List<Congreso> congresos=(List<Congreso>) request.getAttribute("congresos");
  Integer congresoIdSel=(Integer) request.getAttribute("congresoIdSel");
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title>Actividades</title></head>
    <body>
        <h2>Actividades</h2>
        <p><a href="<%=request.getContextPath()%>/organizador/dashboard">← Volver</a></p>

        <form method="get" action="<%=request.getContextPath()%>/organizador/actividades">
            <label>Congreso:</label>
            <select name="congresoId" onchange="this.form.submit()">
                <option value="">— Selecciona —</option>
                <% if (congresos!=null) for (Congreso c: congresos){ %>
                <option value="<%=c.getId()%>" <%= (congresoIdSel!=null && congresoIdSel.equals(c.getId()))?"selected":"" %>><%=c.getTitulo()%></option>
                <% } %>
            </select>
            <noscript><button type="submit">Ver</button></noscript>
        </form>

        <p><a href="<%=request.getContextPath()%>/organizador/actividades/crear">+ Nueva actividad</a></p>

        <table border="1" cellpadding="6" cellspacing="0">
            <thead><tr>
                    <th>Nombre</th><th>Tipo</th><th>Inicio</th><th>Fin</th><th>Salón</th><th>Congreso</th><th>Cupo</th><th>Acciones</th>
                </tr></thead>
            <tbody>
                <% if (actividades!=null) for (Actividad a: actividades) { %>
                <tr>
                    <td><%=a.getNombre()%></td>
                    <td><%=a.getTipoActividad()%></td>
                    <td><%=a.getFechaHoraInicio()%></td>
                    <td><%=a.getFechaHoraFin()%></td>
                    <td><%=a.getSalonId()%></td>
                    <td><%=a.getCongresoId()%></td>
                    <td><%=a.getCupoMaximo()!=null?a.getCupoMaximo():"—"%></td>
                    <td><a href="<%=request.getContextPath()%>/organizador/actividades/editar?id=<%=a.getId()%>">Editar</a></td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
