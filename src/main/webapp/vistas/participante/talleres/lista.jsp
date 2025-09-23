<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,com.codenbugs.sistemacongresos.modelos.Actividad" %>
<%
  List<Actividad> actividades = (List<Actividad>) request.getAttribute("actividades");
  String ok = (String) session.getAttribute("flash_ok");
  String err = (String) session.getAttribute("flash_error");
  if (ok!=null) session.removeAttribute("flash_ok");
  if (err!=null) session.removeAttribute("flash_error");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Explorar talleres y ponencias</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/">SistemaCongresos</a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="<%=request.getContextPath()%>/participante/dashboard">← Volver al Dashboard</a>
                </div>
            </div>
        </nav>

        <main class="container py-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Explorar talleres y ponencias</h2>
                <a href="<%=request.getContextPath()%>/participante/talleres/mis" class="btn btn-outline-primary">Todas mis reservas</a>
            </div>

            <% if (ok!=null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%=ok%>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } %>

            <% if (err!=null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%=err%>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } %>

            <% if (actividades!=null && !actividades.isEmpty()) { %>
            <div class="row">
                <% for (Actividad a : actividades) {
                    boolean esTaller = "TALLER".equals(a.getTipoActividad());
                %>
                <div class="col-md-6 col-lg-4 mb-4">
                    <div class="card shadow-sm h-100">
                        <div class="card-body">
                            <h5 class="card-title">
                                <%= a.getNombre() %>
                                <span class="badge bg-secondary"><%= a.getTipoActividad() %></span>
                            </h5>
                            <p class="card-text"><b>Descripción:</b> <%= a.getDescripcion()!=null?a.getDescripcion():"Sin descripción" %></p>
                            <p class="card-text"><b>Horario:</b> <%= a.getFechaHoraInicio() %> → <%= a.getFechaHoraFin() %></p>
                            <p class="card-text"><b>Salón:</b> <%= a.getSalonId() %></p>
                        </div>
                        <div class="card-footer">
                            <form method="post" action="<%=request.getContextPath()%>/participante/talleres/reservar">
                                <input type="hidden" name="actividad_id" value="<%=a.getId()%>"/>
                                <button type="submit" class="btn btn-primary w-100">
                                    <%= esTaller ? "Reservar cupo" : "Agregar a mi agenda" %>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <div class="text-center py-5">
                <div class="card shadow-sm">
                    <div class="card-body py-5">
                        <h5 class="text-muted">No hay actividades disponibles</h5>
                        <p class="text-muted">Para ver talleres y ponencias debes estar inscrito en un congreso.</p>
                        <a href="<%=request.getContextPath()%>/participante/congresos" class="btn btn-primary">Explorar congresos</a>
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
