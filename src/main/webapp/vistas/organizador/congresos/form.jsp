<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,java.math.BigDecimal,com.codenbugs.sistemacongresos.modelos.Congreso" %>
<%
  String error = (String) request.getAttribute("error");
  Congreso c = (Congreso) request.getAttribute("congreso");
  boolean isEdit = (c != null && c.getId() != null);
  String action = isEdit ? (request.getContextPath()+"/organizador/congresos/editar")
                         : (request.getContextPath()+"/organizador/congresos/crear");
  List<Integer> instituciones = (List<Integer>) request.getAttribute("instituciones");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title><%= isEdit ? "Editar" : "Crear" %> congreso</title>
    </head>
    <body>
        <h2><%= isEdit ? "Editar" : "Crear" %> congreso</h2>
        <p><a href="<%=request.getContextPath()%>/organizador/congresos">← Volver</a></p>

        <% if (error != null) { %>
        <div style="color:red;"><%= error %></div>
        <% } %>

        <form method="post" action="<%=action%>">
            <% if (isEdit) { %><input type="hidden" name="id" value="<%=c.getId()%>"/><% } %>

            <div>
                <label>Título *</label><br/>
                <input type="text" name="titulo" required
                       value="<%= isEdit ? c.getTitulo() : (request.getParameter("titulo")!=null?request.getParameter("titulo"):"") %>"/>
            </div>

            <div>
                <label>Descripción</label><br/>
                <textarea name="descripcion" rows="3"><%= isEdit ? (c.getDescripcion()!=null?c.getDescripcion():"")
      : (request.getParameter("descripcion")!=null?request.getParameter("descripcion"):"") %></textarea>
            </div>

            <div>
                <label>Fecha inicio *</label><br/>
                <input type="date" name="fecha_inicio" required
                       value="<%= isEdit ? String.valueOf(c.getFechaInicio()) : (request.getParameter("fecha_inicio")!=null?request.getParameter("fecha_inicio"):"") %>"/>
            </div>

            <div>
                <label>Fecha fin *</label><br/>
                <input type="date" name="fecha_fin" required
                       value="<%= isEdit ? String.valueOf(c.getFechaFin()) : (request.getParameter("fecha_fin")!=null?request.getParameter("fecha_fin"):"") %>"/>
            </div>

            <div>
                <label>Precio (GTQ) *</label><br/>
                <input type="number" step="0.01" min="35" name="precio" required
                       value="<%= isEdit ? c.getPrecio() : (request.getParameter("precio")!=null?request.getParameter("precio"):"") %>"/>
            </div>

            <div>
                <label>Ubicación *</label><br/>
                <input type="text" name="ubicacion" required
                       value="<%= isEdit ? c.getUbicacion() : (request.getParameter("ubicacion")!=null?request.getParameter("ubicacion"):"") %>"/>
            </div>

            <div>
                <label>Institución *</label><br/>
                <select name="institucion_id" required>
                    <% if (instituciones != null) {
                         Integer sel = isEdit ? c.getInstitucionId() : (request.getParameter("institucion_id")!=null ? Integer.valueOf(request.getParameter("institucion_id")) : null);
                         for (Integer iid : instituciones) { %>
                    <option value="<%=iid%>" <%= (sel!=null && sel.equals(iid)) ? "selected":"" %>><%=iid%></option>
                    <% } } %>
                </select>
            </div>

            <div>
                <label>Comisión (%)</label><br/>
                <input type="number" step="0.01" name="comision"
                       value="<%= isEdit && c.getComisionPorcentaje()!=null ? c.getComisionPorcentaje() :
                        (request.getParameter("comision")!=null?request.getParameter("comision"):"") %>"/>
                <small>Si se deja vacío, se usará la global o 10% por defecto.</small>
            </div>

            <div>
                <label><input type="checkbox" name="activo"
                              <%= (isEdit ? (c.getActivo()!=null && c.getActivo()) : "on".equals(request.getParameter("activo"))) ? "checked":"" %> />
                    Activo
                </label>
            </div>

            <button type="submit"><%= isEdit ? "Guardar cambios" : "Crear" %></button>
        </form>
    </body>
</html>
