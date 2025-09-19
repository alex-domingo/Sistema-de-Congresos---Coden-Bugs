<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.codenbugs.sistemacongresos.modelos.Institucion" %>
<%
  List<Institucion> instituciones = (List<Institucion>) request.getAttribute("instituciones");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Instituciones</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/estilos.css">
    </head>
    <body>
        <h2>Instituciones</h2>
        <p><a href="<%=request.getContextPath()%>/admin/dashboard">← Volver al dashboard</a></p>
        <p><a href="<%=request.getContextPath()%>/admin/instituciones/crear">+ Nueva institución</a></p>

        <table border="1" cellpadding="6" cellspacing="0">
            <thead>
                <tr>
                    <th>ID</th><th>Nombre</th><th>Descripción</th><th>Activa</th><th>Creada</th><th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% if (instituciones != null) {
       for (Institucion i : instituciones) { %>
                <tr>
                    <td><%=i.getId()%></td>
                    <td><%=i.getNombre()%></td>
                    <td><%=i.getDescripcion()!=null?i.getDescripcion():""%></td>
                    <td><%=i.getActiva() ? "Sí" : "No"%></td>
                    <td><%=i.getFechaCreacion()%></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/admin/instituciones/editar?id=<%=i.getId()%>">Editar</a>
                        |
                        <form action="<%=request.getContextPath()%>/admin/instituciones/estado" method="post" style="display:inline">
                            <input type="hidden" name="id" value="<%=i.getId()%>"/>
                            <input type="hidden" name="activa" value="<%= !i.getActiva() %>"/>
                            <button type="submit"><%= i.getActiva() ? "Desactivar" : "Activar" %></button>
                        </form>
                    </td>
                </tr>
                <% } } %>
            </tbody>
        </table>
    </body>
</html>
