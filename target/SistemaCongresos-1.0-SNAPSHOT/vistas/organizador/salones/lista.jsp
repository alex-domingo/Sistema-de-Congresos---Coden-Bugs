<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Salon" %>
<%
  List<Salon> salones=(List<Salon>) request.getAttribute("salones");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Salones</title>
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
                <h2>Salones</h2>
                <a href="<%=request.getContextPath()%>/organizador/salones/crear" class="btn btn-primary">+ Nuevo salón</a>
            </div>

            <% if (salones != null && !salones.isEmpty()) { %>
            <div class="card shadow-sm">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover mb-0">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Ubicación</th>
                                    <th>Capacidad</th>
                                    <th>Congreso</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Salon s: salones) { %>
                                <tr>
                                    <td><strong>#<%=s.getId()%></strong></td>
                                    <td><strong><%=s.getNombre()%></strong></td>
                                    <td>
                                        <% if (s.getUbicacion() != null && !s.getUbicacion().isEmpty()) { %>
                                        <%=s.getUbicacion()%>
                                        <% } else { %>
                                        <span class="text-muted">—</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <% if (s.getCapacidad() != null) { %>
                                        <span class="badge bg-secondary"><%=s.getCapacidad()%> personas</span>
                                        <% } else { %>
                                        <span class="text-muted">—</span>
                                        <% } %>
                                    </td>
                                    <td>#<%=s.getCongresoId()%></td>
                                    <td>
                                        <a href="<%=request.getContextPath()%>/organizador/salones/editar?id=<%=s.getId()%>" 
                                           class="btn btn-sm btn-outline-primary">Editar</a>
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
                        <h5 class="text-muted">No hay salones creados</h5>
                        <p class="text-muted">Comienza creando tu primer salón.</p>
                        <a href="<%=request.getContextPath()%>/organizador/salones/crear" class="btn btn-primary">Crear primer salón</a>
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
