<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.codenbugs.sistemacongresos.modelos.Institucion" %>
<%
  String error = (String) request.getAttribute("error");
  Institucion inst = (Institucion) request.getAttribute("inst");
  boolean isEdit = (inst != null && inst.getId() != null);
  String action = isEdit ? (request.getContextPath()+"/admin/instituciones/editar") 
                         : (request.getContextPath()+"/admin/instituciones/crear");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Editar" : "Crear" %> institución</title>
    </head>
    <body>
        <h2><%= isEdit ? "Editar" : "Crear" %> institución</h2>
        <p><a href="<%=request.getContextPath()%>/admin/instituciones">← Volver</a></p>

        <% if (error != null) { %>
        <div style="color:red;"><%= error %></div>
        <% } %>

        <form method="post" action="<%=action%>">
            <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= inst.getId() %>"/>
            <% } %>

            <div>
                <label>Nombre *</label><br/>
                <input type="text" name="nombre" required
                       value="<%= isEdit ? inst.getNombre() : (request.getAttribute("nombre")!=null?request.getAttribute("nombre"):"") %>"/>
            </div>
            <div>
                <label>Descripción</label><br/>
                <textarea name="descripcion" rows="3"><%= isEdit ? (inst.getDescripcion()!=null?inst.getDescripcion():"") 
          : (request.getAttribute("descripcion")!=null?request.getAttribute("descripcion"):"") %></textarea>
            </div>
            <div>
                <label>
                    <input type="checkbox" name="activa"
                           <%= (isEdit ? (inst.getActiva()!=null && inst.getActiva()) 
                         : ("true".equals(String.valueOf(request.getAttribute("activa"))))) ? "checked" : "" %> />
                    Activa
                </label>
            </div>

            <button type="submit"><%= isEdit ? "Guardar cambios" : "Crear" %></button>
        </form>
    </body>
</html>
