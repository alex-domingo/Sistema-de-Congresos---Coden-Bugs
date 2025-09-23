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
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const selCongreso = document.querySelector('select[name="congreso_id"]');
                const selSalon = document.querySelector('select[name="salon_id"]');
                const tipoActividad = document.querySelector('select[name="tipo_actividad"]');
                const cupoDiv = document.getElementById('cupoDiv');

                function toggleCupoField() {
                    if (tipoActividad.value === 'TALLER') {
                        cupoDiv.style.display = 'block';
                    } else {
                        cupoDiv.style.display = 'none';
                    }
                }

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

                // Inicializamos estado del campo cupo
                toggleCupoField();

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

                // Mostrar/ocultar campo cupo según tipo
                tipoActividad.addEventListener('change', toggleCupoField);
            });
        </script>
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/">SistemaCongresos</a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="<%=request.getContextPath()%>/organizador/actividades">← Volver a Actividades</a>
                </div>
            </div>
        </nav>

        <main class="container py-4">
            <div class="row justify-content-center">
                <div class="col-12 col-lg-8">
                    <h2 class="mb-4"><%=isEdit?"Editar":"Crear"%> actividad</h2>

                    <% if (error!=null){ %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <%=error%>
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <% } %>

                    <div class="card shadow-sm">
                        <div class="card-body">
                            <form method="post" action="<%=action%>">
                                <% if (isEdit){ %>
                                <input type="hidden" name="id" value="<%=a.getId()%>"/>
                                <% } %>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Congreso *</label>
                                        <select class="form-select" name="congreso_id" required>
                                            <option value="">— Selecciona —</option>
                                            <% Integer selCong = isEdit? a.getCongresoId() : (request.getParameter("congreso_id")!=null?Integer.valueOf(request.getParameter("congreso_id")):null);
                                               if (congresos!=null) for (var c: congresos){ %>
                                            <option value="<%=c.getId()%>" <%= (selCong!=null && selCong.equals(c.getId()))?"selected":"" %>><%=c.getTitulo()%></option>
                                            <% } %>
                                        </select>
                                    </div>

                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Salón *</label>
                                        <select class="form-select" name="salon_id" required>
                                            <option value="">— Selecciona —</option>
                                            <%
                                               if (isEdit && a.getSalonId()!=null) { %>
                                            <option value="<%=a.getSalonId()%>" selected>Actual (ID <%=a.getSalonId()%>)</option>
                                            <% } %>
                                        </select>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Nombre de la actividad *</label>
                                    <input type="text" class="form-control" name="nombre" required
                                           value="<%= isEdit 
                                                     ? a.getNombre() 
                                                     : (request.getParameter("nombre")!=null 
                                                          ? request.getParameter("nombre") 
                                                          : "") %>"/>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Descripción</label>
                                    <textarea class="form-control" name="descripcion" rows="3"><%=isEdit&&(a.getDescripcion()!=null)?a.getDescripcion():(request.getParameter("descripcion")!=null?request.getParameter("descripcion"):"")%></textarea>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Tipo *</label>
                                        <select class="form-select" name="tipo_actividad" required>
                                            <option value="PONENCIA" <%= isEdit && "PONENCIA".equals(a.getTipoActividad()) ? "selected":"" %>>PONENCIA</option>
                                            <option value="TALLER" <%= isEdit && "TALLER".equals(a.getTipoActividad()) ? "selected":"" %>>TALLER</option>
                                        </select>
                                    </div>

                                    <div class="col-md-6 mb-3" id="cupoDiv" style="display: none;">
                                        <label class="form-label">Cupo (solo para TALLER)</label>
                                        <input type="number" class="form-control" name="cupo_maximo" min="1" 
                                               value="<%= isEdit && a.getCupoMaximo()!=null ? a.getCupoMaximo() : (request.getParameter("cupo_maximo")!=null?request.getParameter("cupo_maximo"):"") %>"/>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Inicio *</label>
                                        <div class="row g-2">
                                            <div class="col-6">
                                                <input type="date" class="form-control" name="fecha_inicio" required 
                                                       value="<%= isEdit? a.getFechaHoraInicio().toString().substring(0,10) : (request.getParameter("fecha_inicio")!=null?request.getParameter("fecha_inicio"):"") %>"/>
                                            </div>
                                            <div class="col-6">
                                                <input type="time" class="form-control" name="hora_inicio" required 
                                                       value="<%= isEdit? a.getFechaHoraInicio().toString().substring(11,16) : (request.getParameter("hora_inicio")!=null?request.getParameter("hora_inicio"):"") %>"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Fin *</label>
                                        <div class="row g-2">
                                            <div class="col-6">
                                                <input type="date" class="form-control" name="fecha_fin" required 
                                                       value="<%= isEdit? a.getFechaHoraFin().toString().substring(0,10) : (request.getParameter("fecha_fin")!=null?request.getParameter("fecha_fin"):"") %>"/>
                                            </div>
                                            <div class="col-6">
                                                <input type="time" class="form-control" name="hora_fin" required 
                                                       value="<%= isEdit? a.getFechaHoraFin().toString().substring(11,16) : (request.getParameter("hora_fin")!=null?request.getParameter("hora_fin"):"") %>"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary btn-lg"><%=isEdit?"Guardar":"Crear"%> actividad</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <footer class="border-top py-3 mt-4">
            <div class="container text-center text-muted small">
                © <%= java.time.Year.now() %> SistemaCongresos · Desarrollado por Alex Domingo
            </div>
        </footer>

        <script src="<%=request.getContextPath()%>/recursos/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
