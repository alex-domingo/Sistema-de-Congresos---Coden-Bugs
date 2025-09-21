<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Actividad,com.codenbugs.sistemacongresos.modelos.Congreso,com.codenbugs.sistemacongresos.modelos.Salon" %>
<%
  String error=(String) request.getAttribute("error");
  Actividad a=(Actividad) request.getAttribute("actividad");
  boolean isEdit = (a!=null && a.getId()!=null);
  String action = isEdit ? (request.getContextPath()+"/organizador/actividades/editar")
                         : (request.getContextPath()+"/organizador/actividades/crear");
  List<Congreso> congresos=(List<Congreso>) request.getAttribute("congresos");
  List<Salon> salones=(List<Salon>) request.getAttribute("salones");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title><%=isEdit?"Editar":"Crear"%> actividad</title>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const selCongreso = document.querySelector('select[name="congreso_id"]');
                const selSalon = document.querySelector('select[name="salon_id"]');

                async function cargarSalonesAjax(congresoId, preselectId) {
                    if (!congresoId) {
                        selSalon.innerHTML = '<option value="">— Selecciona —</option>';
                        return;
                    }
                    try {
                        const res = await fetch('<%=request.getContextPath()%>/organizador/salones/porCongreso?congresoId=' + encodeURIComponent(congresoId), {
                            headers: {'X-Requested-With': 'XMLHttpRequest'}
                        });
                        const optionsHtml = await res.text();
                        selSalon.innerHTML = optionsHtml;
                        if (preselectId) {
                            selSalon.value = String(preselectId);
                        }
                    } catch (e) {
                        selSalon.innerHTML = '<option value="">— Error cargando salones —</option>';
                    }
                }

                // Para la carga inicial: si estamos editando, precargamos salones del congreso actual
            <% Integer initCong = isEdit ? a.getCongresoId() : null;
             Integer initSalon = isEdit ? a.getSalonId() : null; %>
            <% if (initCong != null) { %>
                cargarSalonesAjax('<%=initCong%>', '<%=initSalon%>');
            <% } %>

                // Cambio sin recargar la página
                selCongreso.addEventListener('change', function () {
                    cargarSalonesAjax(this.value, null);
                });
            });
        </script>
    </head>
    <body>
        <h2><%=isEdit?"Editar":"Crear"%> actividad</h2>
        <p><a href="<%=request.getContextPath()%>/organizador/actividades">← Volver</a></p>
        <% if (error!=null){ %><div style="color:red;"><%=error%></div><% } %>

        <form method="post" action="<%=action%>">
            <% if (isEdit){ %><input type="hidden" name="id" value="<%=a.getId()%>"/><% } %>

            <div>
                <label>Congreso *</label><br/>
                <select name="congreso_id" required>
                    <option value="">— Selecciona —</option>
                    <% Integer selCong = isEdit? a.getCongresoId() : (request.getParameter("congreso_id")!=null?Integer.valueOf(request.getParameter("congreso_id")):null);
       if (congresos!=null) for (var c: congresos){ %>
                    <option value="<%=c.getId()%>" <%= (selCong!=null && selCong.equals(c.getId()))?"selected":"" %>><%=c.getTitulo()%></option>
                    <% } %>
                </select>
            </div>

            <div>
                <label>Salón *</label><br/>
                <select name="salon_id" required>
                    <option value="">— Selecciona —</option>
                    <%
       if (isEdit && a.getSalonId()!=null) { %>
                    <option value="<%=a.getSalonId()%>" selected>Actual (ID <%=a.getSalonId()%>)</option>
                    <% } %>
                </select>
            </div>

            <div>
                <label>Nombre de la actividad *</label><br/>
                <input type="text" name="nombre" required
                       value="<%= isEdit 
                                 ? a.getNombre() 
                                 : (request.getParameter("nombre")!=null 
                                      ? request.getParameter("nombre") 
                                      : "") %>"/>
            </div>

            <div>
                <label>Descripción</label><br/>
                <textarea name="descripcion" rows="3"><%=isEdit&&(a.getDescripcion()!=null)?a.getDescripcion():(request.getParameter("descripcion")!=null?request.getParameter("descripcion"):"")%></textarea>
            </div>

            <div>
                <label>Tipo *</label><br/>
                <select name="tipo_actividad" required>
                    <option value="PONENCIA" <%= isEdit && "PONENCIA".equals(a.getTipoActividad()) ? "selected":"" %>>PONENCIA</option>
                    <option value="TALLER" <%= isEdit && "TALLER".equals(a.getTipoActividad()) ? "selected":"" %>>TALLER</option>
                </select>
            </div>

            <div>
                <label>Inicio *</label><br/>
                <input type="date" name="fecha_inicio" required value="<%= isEdit? a.getFechaHoraInicio().toString().substring(0,10) : (request.getParameter("fecha_inicio")!=null?request.getParameter("fecha_inicio"):"") %>"/>
                <input type="time" name="hora_inicio" required value="<%= isEdit? a.getFechaHoraInicio().toString().substring(11,16) : (request.getParameter("hora_inicio")!=null?request.getParameter("hora_inicio"):"") %>"/>
            </div>

            <div>
                <label>Fin *</label><br/>
                <input type="date" name="fecha_fin" required value="<%= isEdit? a.getFechaHoraFin().toString().substring(0,10) : (request.getParameter("fecha_fin")!=null?request.getParameter("fecha_fin"):"") %>"/>
                <input type="time" name="hora_fin" required value="<%= isEdit? a.getFechaHoraFin().toString().substring(11,16) : (request.getParameter("hora_fin")!=null?request.getParameter("hora_fin"):"") %>"/>
            </div>

            <div id="cupoDiv">
                <label>Cupo (solo para TALLER)</label><br/>
                <input type="number" name="cupo_maximo" min="1" value="<%= isEdit && a.getCupoMaximo()!=null ? a.getCupoMaximo() : (request.getParameter("cupo_maximo")!=null?request.getParameter("cupo_maximo"):"") %>"/>
            </div>

            <button type="submit"><%=isEdit?"Guardar":"Crear"%></button>
        </form>
    </body>
</html>
