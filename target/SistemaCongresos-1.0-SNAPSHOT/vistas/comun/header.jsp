<%@ page contentType="text/html; charset=UTF-8" %>
<%
  String ctx = request.getContextPath();
  String tituloPagina = (String) request.getAttribute("tituloPagina");
  if (tituloPagina == null) tituloPagina = "Sistema de Congresos";
  com.codenbugs.sistemacongresos.modelos.Usuario sesionU =
      (com.codenbugs.sistemacongresos.modelos.Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title><%= tituloPagina %></title>

        <link rel="stylesheet" href="<%=ctx%>/recursos/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=ctx%>/recursos/css/app.css">
    </head>
    <body class="bg-light">

        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand fw-bold" href="<%=ctx%>/">SistemaCongresos</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#topnav">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div id="topnav" class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto"></ul>

                    <div class="d-flex align-items-center gap-2">
                        <% if (sesionU == null) { %>
                        <a class="btn btn-sm btn-light" href="<%=ctx%>/auth/login">Entrar</a>
                        <a class="btn btn-sm btn-outline-light" href="<%=ctx%>/auth/registro">Crear cuenta</a>
                        <% } else { %>
                        <img src="<%=ctx%>/usuario/foto?id=<%= sesionU.getId() %>&v=<%=System.currentTimeMillis()%>"
                             alt="avatar" width="36" height="36"
                             class="rounded-circle border border-light-subtle"
                             style="object-fit:cover">
                        <span class="text-white-50 small"><%= sesionU.getEmail() %></span>
                        <a class="btn btn-sm btn-light ms-1" href="<%=ctx%>/auth/logout">Salir</a>
                        <% } %>
                    </div>
                </div>
            </div>
        </nav>

        <main class="container py-4">
