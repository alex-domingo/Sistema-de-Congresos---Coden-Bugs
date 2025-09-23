<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.codenbugs.sistemacongresos.modelos.Congreso" %>
<%
  List<Congreso> congresos = (List<Congreso>) request.getAttribute("congresos");
  String ok = (String) session.getAttribute("flash_ok");
  String err = (String) session.getAttribute("flash_error");
  if (ok != null) session.removeAttribute("flash_ok");
  if (err != null) session.removeAttribute("flash_error");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Explorar Congresos</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/estilos.css">
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
            <h2 class="mb-4">Explorar Congresos</h2>

            <% if (ok != null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= ok %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } %>

            <% if (err != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= err %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } %>

            <div class="row">
                <% if (congresos != null) {
                    for (Congreso c : congresos) { %>
                <div class="col-md-6 col-lg-4 mb-4">
                    <div class="card shadow-sm h-100">
                        <div class="card-body">
                            <h3 class="h5 card-title"><%= c.getTitulo() %></h3>
                            <p class="card-text"><b>Descripción:</b> <%= c.getDescripcion()!=null ? c.getDescripcion() : "Sin descripción" %></p>
                            <p class="card-text"><b>Fechas:</b> <%= c.getFechaInicio() %> → <%= c.getFechaFin() %></p>
                            <p class="card-text"><b>Precio:</b> Q<%= c.getPrecio() %></p>
                            <p class="card-text"><b>Ubicación:</b> <%= c.getUbicacion() %></p>
                        </div>
                        <div class="card-footer">
                            <form method="post" action="<%=request.getContextPath()%>/participante/congresos/inscribir">
                                <input type="hidden" name="congreso_id" value="<%= c.getId() %>" />
                                <button type="submit" class="btn btn-primary w-100">Inscribirme</button>
                            </form>
                        </div>
                    </div>
                </div>
                <% } } %>
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
