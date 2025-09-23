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
    <head>
        <meta charset="UTF-8">
        <title><%=isEdit?"Editar":"Crear"%> salón</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/">SistemaCongresos</a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="<%=request.getContextPath()%>/organizador/salones">← Volver a Salones</a>
                </div>
            </div>
        </nav>

        <main class="container py-4">
            <div class="row justify-content-center">
                <div class="col-12 col-lg-6">
                    <h2 class="mb-4"><%=isEdit?"Editar":"Crear"%> salón</h2>

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
                                <input type="hidden" name="id" value="<%=s.getId()%>"/>
                                <% } %>

                                <div class="mb-3">
                                    <label class="form-label">Nombre *</label>
                                    <input type="text" class="form-control" name="nombre" required 
                                           value="<%=isEdit?s.getNombre():""%>"/>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Ubicación</label>
                                    <input type="text" class="form-control" name="ubicacion" 
                                           value="<%=isEdit&&(s.getUbicacion()!=null)?s.getUbicacion():""%>"/>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Capacidad</label>
                                    <input type="number" class="form-control" name="capacidad" min="1" 
                                           value="<%=isEdit&&s.getCapacidad()!=null?s.getCapacidad():""%>"/>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Descripción</label>
                                    <textarea class="form-control" name="descripcion" rows="3"><%=isEdit&&(s.getDescripcion()!=null)?s.getDescripcion():""%></textarea>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Congreso *</label>
                                    <select class="form-select" name="congreso_id" required>
                                        <% if (congresos!=null) {
                                             Integer sel = isEdit? s.getCongresoId() : null;
                                             for (var c : congresos) { %>
                                        <option value="<%=c.getId()%>" <%= (sel!=null && sel.equals(c.getId()))?"selected":"" %>>
                                            <%=c.getTitulo()%> (ID <%=c.getId()%>)
                                        </option>
                                        <% } } %>
                                    </select>
                                </div>

                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary btn-lg">
                                        <%=isEdit?"Guardar":"Crear"%> salón
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
