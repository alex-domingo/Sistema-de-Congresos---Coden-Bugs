<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Congreso" %>
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
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/">SistemaCongresos</a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="<%=request.getContextPath()%>/organizador/congresos">← Volver a Congresos</a>
                </div>
            </div>
        </nav>

        <main class="container py-4">
            <div class="row justify-content-center">
                <div class="col-12 col-lg-8">
                    <h2 class="mb-4"><%= isEdit ? "Editar" : "Crear" %> congreso</h2>

                    <% if (error != null) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <%= error %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <% } %>

                    <div class="card shadow-sm">
                        <div class="card-body">
                            <form method="post" action="<%=action%>">
                                <% if (isEdit) { %>
                                <input type="hidden" name="id" value="<%=c.getId()%>"/>
                                <% } %>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Título *</label>
                                        <input type="text" class="form-control" name="titulo" required
                                               value="<%= isEdit ? c.getTitulo() : (request.getParameter("titulo")!=null?request.getParameter("titulo"):"") %>"/>
                                    </div>

                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Institución *</label>
                                        <select class="form-select" name="institucion_id" required>
                                            <% if (instituciones != null) {
                                                 Integer sel = isEdit ? c.getInstitucionId() : (request.getParameter("institucion_id")!=null ? Integer.valueOf(request.getParameter("institucion_id")) : null);
                                                 for (Integer iid : instituciones) { %>
                                            <option value="<%=iid%>" <%= (sel!=null && sel.equals(iid)) ? "selected":"" %>><%=iid%></option>
                                            <% } } %>
                                        </select>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Descripción</label>
                                    <textarea class="form-control" name="descripcion" rows="3"><%= isEdit ? (c.getDescripcion()!=null?c.getDescripcion():"")
                                          : (request.getParameter("descripcion")!=null?request.getParameter("descripcion"):"") %></textarea>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Fecha inicio *</label>
                                        <input type="date" class="form-control" name="fecha_inicio" required
                                               value="<%= isEdit ? String.valueOf(c.getFechaInicio()) : (request.getParameter("fecha_inicio")!=null?request.getParameter("fecha_inicio"):"") %>"/>
                                    </div>

                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Fecha fin *</label>
                                        <input type="date" class="form-control" name="fecha_fin" required
                                               value="<%= isEdit ? String.valueOf(c.getFechaFin()) : (request.getParameter("fecha_fin")!=null?request.getParameter("fecha_fin"):"") %>"/>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Precio (GTQ) *</label>
                                        <input type="number" step="0.01" min="35" class="form-control" name="precio" required
                                               value="<%= isEdit ? c.getPrecio() : (request.getParameter("precio")!=null?request.getParameter("precio"):"") %>"/>
                                    </div>

                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Comisión (%)</label>
                                        <input type="number" step="0.01" class="form-control" name="comision"
                                               value="<%= isEdit && c.getComisionPorcentaje()!=null ? c.getComisionPorcentaje() :
                                                        (request.getParameter("comision")!=null?request.getParameter("comision"):"") %>"/>
                                        <div class="form-text">Si se deja vacío, se usará la global o 10% por defecto.</div>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Ubicación *</label>
                                    <input type="text" class="form-control" name="ubicacion" required
                                           value="<%= isEdit ? c.getUbicacion() : (request.getParameter("ubicacion")!=null?request.getParameter("ubicacion"):"") %>"/>
                                </div>

                                <div class="mb-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="activo" id="activo"
                                               <%= (isEdit ? (c.getActivo()!=null && c.getActivo()) : "on".equals(request.getParameter("activo"))) ? "checked":"" %> />
                                        <label class="form-check-label" for="activo">
                                            Activo
                                        </label>
                                    </div>
                                </div>

                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary btn-lg">
                                        <%= isEdit ? "Guardar cambios" : "Crear congreso" %>
                                    </button>
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
