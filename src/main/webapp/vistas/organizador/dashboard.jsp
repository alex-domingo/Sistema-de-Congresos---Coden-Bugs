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
        <title>Organizador – Dashboard</title>
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
                    <h2 class="mb-4">Dashboard Organizador</h2>

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
                        <div class="col-md-6 col-lg-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Mis Congresos</h5>
                                    <p class="card-text">Gestiona tus congresos organizados</p>
                                    <a href="<%=request.getContextPath()%>/organizador/congresos" class="btn btn-primary">Acceder</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-lg-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Salones</h5>
                                    <p class="card-text">Administra los salones disponibles</p>
                                    <a href="<%=request.getContextPath()%>/organizador/salones" class="btn btn-primary">Acceder</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-lg-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Actividades</h5>
                                    <p class="card-text">Gestiona actividades y eventos</p>
                                    <a href="<%=request.getContextPath()%>/organizador/actividades" class="btn btn-primary">Acceder</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-lg-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Convocatorias & Trabajos</h5>
                                    <p class="card-text">Administra convocatorias y trabajos</p>
                                    <a href="#" class="btn btn-primary">Acceder</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-lg-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">Reportes del Congreso</h5>
                                    <p class="card-text">Consulta reportes y estadísticas</p>
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
