<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%
  List<String> mis = (List<String>) request.getAttribute("misReservas");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Todas mis reservas</title>
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
                <h2>Todas mis reservas</h2>
                <a href="<%=request.getContextPath()%>/participante/talleres" class="btn btn-outline-primary">Explorar talleres y ponencias</a>
            </div>

            <% if (mis!=null && !mis.isEmpty()) { %>
            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="list-group list-group-flush">
                        <% for (String linea : mis) { %>
                        <div class="list-group-item"><%= linea %></div>
                        <% } %>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="text-center py-5">
                <div class="card shadow-sm">
                    <div class="card-body py-5">
                        <h5 class="text-muted">No tienes reservas todavía</h5>
                        <p class="text-muted">Comienza explorando los talleres y ponencias disponibles.</p>
                        <a href="<%=request.getContextPath()%>/participante/talleres" class="btn btn-primary">Explorar talleres y ponencias</a>
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
