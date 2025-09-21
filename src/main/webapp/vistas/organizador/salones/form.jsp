<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Salon" %>
<%
  String error=(String) request.getAttribute("error");
  Salon s=(Salon) request.getAttribute("salon");
  boolean isEdit = (s!=null && s.getId()!=null);
  String action = isEdit ? (request.getContextPath()+"/organizador/salones/editar")
                         : (request.getContextPath()+"/organizador/salones/crear");
  List<com.codenbugs.sistemacongresos.modelos.Congreso> congresos =
       (List<com.codenbugs.sistemacongresos.modelos.Congreso>) request.getAttribute("congresos");
%>
<!DOCTYPE html>
<html lang="es">
    <head><meta charset="UTF-8"><title><%=isEdit?"Editar":"Crear"%> salón</title></head>
    <body>
        <h2><%=isEdit?"Editar":"Crear"%> salón</h2>
        <p><a href="<%=request.getContextPath()%>/organizador/salones">← Volver</a></p>
        <% if (error!=null){ %><div style="color:red;"><%=error%></div><% } %>

        <form method="post" action="<%=action%>">
            <% if (isEdit){ %><input type="hidden" name="id" value="<%=s.getId()%>"/><% } %>

            <div>
                <label>Nombre *</label><br/>
                <input type="text" name="nombre" required value="<%=isEdit?s.getNombre():""%>"/>
            </div>

            <div>
                <label>Ubicación</label><br/>
                <input type="text" name="ubicacion" value="<%=isEdit&&(s.getUbicacion()!=null)?s.getUbicacion():""%>"/>
            </div>

            <div>
                <label>Capacidad</label><br/>
                <input type="number" name="capacidad" min="1" value="<%=isEdit&&s.getCapacidad()!=null?s.getCapacidad():""%>"/>
            </div>

            <div>
                <label>Descripción</label><br/>
                <textarea name="descripcion" rows="3"><%=isEdit&&(s.getDescripcion()!=null)?s.getDescripcion():""%></textarea>
            </div>

            <div>
                <label>Congreso *</label><br/>
                <select name="congreso_id" required>
                    <% if (congresos!=null) {
                         Integer sel = isEdit? s.getCongresoId() : null;
                         for (var c : congresos) { %>
                    <option value="<%=c.getId()%>" <%= (sel!=null && sel.equals(c.getId()))?"selected":"" %>>
                        <%=c.getTitulo()%> (ID <%=c.getId()%>)
                    </option>
                    <% } } %>
                </select>
            </div>

            <button type="submit"><%=isEdit?"Guardar":"Crear"%></button>
        </form>
    </body>
</html>
