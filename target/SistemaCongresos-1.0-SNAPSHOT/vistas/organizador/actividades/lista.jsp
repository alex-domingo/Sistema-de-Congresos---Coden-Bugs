<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Actividad,com.codenbugs.sistemacongresos.modelos.Congreso" %>
<%
  List<Actividad> actividades=(List<Actividad>) request.getAttribute("actividades");
  List<Congreso> congresos=(List<Congreso>) request.getAttribute("congresos");
  Integer congresoIdSel=(Integer) request.getAttribute("congresoIdSel");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Actividades</title>
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
                <h2>Actividades</h2>
                <a href="<%=request.getContextPath()%>/organizador/actividades/crear" class="btn btn-primary">+ Nueva actividad</a>
            </div>

            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <form method="get" action="<%=request.getContextPath()%>/organizador/actividades">
                        <div class="row align-items-end">
                            <div class="col-md-6">
                                <label class="form-label">Filtrar por Congreso:</label>
                                <select class="form-select" name="congresoId" onchange="this.form.submit()">
                                    <option value="">— Todos los congresos —</option>
                                    <% if (congresos!=null) for (Congreso c: congresos){ %>
                                    <option value="<%=c.getId()%>" <%= (congresoIdSel!=null && congresoIdSel.equals(c.getId()))?"selected":"" %>><%=c.getTitulo()%></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <noscript><button type="submit" class="btn btn-outline-secondary">Filtrar</button></noscript>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <% if (actividades != null && !actividades.isEmpty()) { %>
            <div class="card shadow-sm">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover mb-0">
                            <thead class="table-dark">
                                <tr>
                                    <th>Nombre</th>
                                    <th>Tipo</th>
                                    <th>Inicio</th>
                                    <th>Fin</th>
                                    <th>Salón</th>
                                    <th>Congreso</th>
                                    <th>Cupo</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Actividad a: actividades) { %>
                                <tr>
                                    <td><strong><%=a.getNombre()%></strong></td>
                                    <td>
                                        <span class="badge <%= "TALLER".equals(a.getTipoActividad()) ? "bg-warning" : "bg-info" %>">
                                            <%=a.getTipoActividad()%>
                                        </span>
                                    </td>
                                    <td><%=a.getFechaHoraInicio()%></td>
                                    <td><%=a.getFechaHoraFin()%></td>
                                    <td><%=a.getSalonId()%></td>
                                    <td><%=a.getCongresoId()%></td>
                                    <td>
                                        <% if (a.getCupoMaximo() != null) { %>
                                        <span class="badge bg-secondary"><%=a.getCupoMaximo()%></span>
                                        <% } else { %>
                                        <span class="text-muted">—</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <a href="<%=request.getContextPath()%>/organizador/actividades/editar?id=<%=a.getId()%>" 
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
                        <h5 class="text-muted">No hay actividades</h5>
                        <p class="text-muted">
                            <% if (congresoIdSel != null) { %>
                            No hay actividades para el congreso seleccionado.
                            <% } else { %>
                            No hay actividades creadas todavía.
                            <% } %>
                        </p>
                        <a href="<%=request.getContextPath()%>/organizador/actividades/crear" class="btn btn-primary">Crear primera actividad</a>
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
