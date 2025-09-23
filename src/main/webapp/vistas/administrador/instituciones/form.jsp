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
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/">SistemaCongresos</a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="<%=request.getContextPath()%>/admin/instituciones">← Volver a Instituciones</a>
                </div>
            </div>
        </nav>

        <main class="container py-4">
            <div class="row justify-content-center">
                <div class="col-12 col-lg-6">
                    <h2 class="mb-4"><%= isEdit ? "Editar" : "Crear" %> institución</h2>

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
                                <input type="hidden" name="id" value="<%= inst.getId() %>"/>
                                <% } %>

                                <div class="mb-3">
                                    <label class="form-label">Nombre *</label>
                                    <input type="text" class="form-control" name="nombre" required
                                           value="<%= isEdit ? inst.getNombre() : (request.getAttribute("nombre")!=null?request.getAttribute("nombre"):"") %>"/>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Descripción</label>
                                    <textarea class="form-control" name="descripcion" rows="3"><%= isEdit ? (inst.getDescripcion()!=null?inst.getDescripcion():"") 
                                              : (request.getAttribute("descripcion")!=null?request.getAttribute("descripcion"):"") %></textarea>
                                </div>

                                <div class="mb-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="activa" id="activa"
                                               <%= (isEdit ? (inst.getActiva()!=null && inst.getActiva()) 
                                                     : ("true".equals(String.valueOf(request.getAttribute("activa"))))) ? "checked" : "" %> />
                                        <label class="form-check-label" for="activa">
                                            Activa
                                        </label>
                                    </div>
                                </div>

                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary btn-lg">
                                        <%= isEdit ? "Guardar cambios" : "Crear institución" %>
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
