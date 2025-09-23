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
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/">SistemaCongresos</a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="<%=request.getContextPath()%>/admin/dashboard">← Volver al Dashboard</a>
                </div>
            </div>
        </nav>

        <main class="container py-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Instituciones</h2>
                <a href="<%=request.getContextPath()%>/admin/instituciones/crear" class="btn btn-primary">+ Nueva institución</a>
            </div>

            <% if (instituciones != null && !instituciones.isEmpty()) { %>
            <div class="card shadow-sm">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover mb-0">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Activa</th>
                                    <th>Creada</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Institucion i : instituciones) { %>
                                <tr>
                                    <td><strong>#<%=i.getId()%></strong></td>
                                    <td><strong><%=i.getNombre()%></strong></td>
                                    <td>
                                        <% if (i.getDescripcion() != null && !i.getDescripcion().isEmpty()) { %>
                                        <%=i.getDescripcion()%>
                                        <% } else { %>
                                        <span class="text-muted">—</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <% if (i.getActiva()) { %>
                                        <span class="badge bg-success">Sí</span>
                                        <% } else { %>
                                        <span class="badge bg-secondary">No</span>
                                        <% } %>
                                    </td>
                                    <td><%=i.getFechaCreacion()%></td>
                                    <td>
                                        <div class="btn-group btn-group-sm" role="group">
                                            <a href="<%=request.getContextPath()%>/admin/instituciones/editar?id=<%=i.getId()%>" 
                                               class="btn btn-outline-primary">Editar</a>
                                            <form action="<%=request.getContextPath()%>/admin/instituciones/estado" method="post" 
                                                  class="d-inline" onsubmit="return confirm('¿Estás seguro de <%= i.getActiva() ? "desactivar" : "activar" %> esta institución?')">
                                                <input type="hidden" name="id" value="<%=i.getId()%>"/>
                                                <input type="hidden" name="activa" value="<%= !i.getActiva() %>"/>
                                                <button type="submit" class="btn <%= i.getActiva() ? "btn-outline-warning" : "btn-outline-success" %>">
                                                    <%= i.getActiva() ? "Desactivar" : "Activar" %>
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="text-center py-5">
                <div class="card shadow-sm">
                    <div class="card-body py-5">
                        <h5 class="text-muted">No hay instituciones</h5>
                        <p class="text-muted">Comienza creando la primera institución.</p>
                        <a href="<%=request.getContextPath()%>/admin/instituciones/crear" class="btn btn-primary">Crear primera institución</a>
                    </div>
                </div>
            </div>
            <% } %>
        </main>

        <footer class="border-top py-3 mt-4">
            <div class="container text-center text-muted small">
                © <%= java.time.Year.now() %> SistemaCongresos · Desarrollado por Alex Domingo
            </div>
        </footer>

        <script src="<%=request.getContextPath()%>/recursos/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
