<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.codenbugs.sistemacongresos.modelos.Congreso" %>
<%
  List<Congreso> congresos = (List<Congreso>) request.getAttribute("congresos");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Mis Congresos</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/">SistemaCongresos</a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="<%=request.getContextPath()%>/organizador/dashboard">← Volver al Dashboard</a>
                </div>
            </div>
        </nav>

        <main class="container py-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Mis Congresos</h2>
                <a href="<%=request.getContextPath()%>/organizador/congresos/crear" class="btn btn-primary">+ Nuevo congreso</a>
            </div>

            <% if (congresos != null && !congresos.isEmpty()) { %>
            <div class="card shadow-sm">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover mb-0">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Título</th>
                                    <th>Fechas</th>
                                    <th>Precio (GTQ)</th>
                                    <th>Ubicación</th>
                                    <th>Activo</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Congreso c : congresos) { %>
                                <tr>
                                    <td><strong>#<%=c.getId()%></strong></td>
                                    <td><strong><%=c.getTitulo()%></strong></td>
                                    <td><%=c.getFechaInicio()%> → <%=c.getFechaFin()%></td>
                                    <td>Q<%=c.getPrecio()%></td>
                                    <td><%=c.getUbicacion()%></td>
                                    <td>
                                        <% if (c.getActivo()) { %>
                                        <span class="badge bg-success">Sí</span>
                                        <% } else { %>
                                        <span class="badge bg-secondary">No</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <div class="btn-group btn-group-sm" role="group">
                                            <a href="<%=request.getContextPath()%>/organizador/congresos/editar?id=<%=c.getId()%>" 
                                               class="btn btn-outline-primary">Editar</a>
                                            <form action="<%=request.getContextPath()%>/organizador/congresos/estado" method="post" 
                                                  class="d-inline" onsubmit="return confirm('¿Estás seguro de <%= c.getActivo() ? "desactivar" : "activar" %> este congreso?')">
                                                <input type="hidden" name="id" value="<%=c.getId()%>"/>
                                                <input type="hidden" name="activo" value="<%= !c.getActivo() %>"/>
                                                <button type="submit" class="btn <%= c.getActivo() ? "btn-outline-warning" : "btn-outline-success" %>">
                                                    <%= c.getActivo() ? "Desactivar" : "Activar" %>
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
                        <h5 class="text-muted">No hay congresos creados</h5>
                        <p class="text-muted">Comienza creando tu primer congreso.</p>
                        <a href="<%=request.getContextPath()%>/organizador/congresos/crear" class="btn btn-primary">Crear primer congreso</a>
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
