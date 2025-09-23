<%@ page contentType="text/html; charset=UTF-8" %>
<%
  String ok = (String) session.getAttribute("flash_ok");
  String error = (String) request.getAttribute("error");
  if (ok != null) session.removeAttribute("flash_ok");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Recargar cartera</title>
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
            <div class="row justify-content-center">
                <div class="col-12 col-md-8 col-lg-6">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2>Recargar cartera</h2>
                    </div>

                    <% if (ok != null) { %>
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <%= ok %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <% } %>

                    <% if (error != null) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <%= error %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <% } %>

                    <div class="card shadow-sm">
                        <div class="card-body">
                            <form method="post" action="<%=request.getContextPath()%>/participante/cartera/recargar">
                                <div class="mb-3">
                                    <label for="monto" class="form-label">Monto a recargar (GTQ) *</label>
                                    <input type="number" 
                                           class="form-control" 
                                           id="monto" 
                                           name="monto" 
                                           min="1" 
                                           step="0.01" 
                                           required 
                                           placeholder="Ingrese el monto">
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Recargar</button>
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
