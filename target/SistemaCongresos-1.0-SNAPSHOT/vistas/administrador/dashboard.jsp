<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.codenbugs.sistemacongresos.modelos.Usuario" %>
<%
  Usuario u = (Usuario) session.getAttribute("usuario");
  String nombre = (u!=null && u.getNombreCompleto()!=null) ? u.getNombreCompleto() : "Usuario";
  String email = (u!=null) ? u.getEmail() : "";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Admin – Dashboard</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/">SistemaCongresos</a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="<%=request.getContextPath()%>/auth/logout">Salir</a>
                </div>
            </div>
        </nav>

        <main class="container py-4">
            <div class="row">
                <div class="col-12">
                    <h2 class="mb-4">Dashboard Admin Sistema</h2>

                    <div class="d-flex align-items-center gap-3 mb-4 p-3 bg-white rounded shadow-sm">
                        <img
                            src="<%=request.getContextPath()%>/usuario/foto?v=<%=System.currentTimeMillis()%>"
                            alt="Mi foto"
                            width="80" height="80"
                            class="rounded-circle border"
                            style="object-fit:cover;"
                            />
                        <div>
                            <div class="fw-bold fs-5">Hola, <%= nombre %></div>
                            <div class="text-muted"><%= email %></div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 col-lg-3 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Instituciones</h5>
                                    <p class="card-text">Gestiona instituciones del sistema</p>
                                    <a href="<%=request.getContextPath()%>/admin/instituciones" class="btn btn-primary">Acceder</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-lg-3 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Usuarios</h5>
                                    <p class="card-text">Administra usuarios del sistema</p>
                                    <a href="#" class="btn btn-primary">Acceder</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-lg-3 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Configuración</h5>
                                    <p class="card-text">Configuración y comisiones</p>
                                    <a href="#" class="btn btn-primary">Acceder</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-lg-3 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Reportes</h5>
                                    <p class="card-text">Reportes del sistema</p>
                                    <a href="#" class="btn btn-primary">Acceder</a>
                                </div>
                            </div>
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
