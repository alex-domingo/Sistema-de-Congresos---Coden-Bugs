<%@ page contentType="text/html; charset=UTF-8" %>
<%
  request.setAttribute("tituloPagina", "Iniciar sesión");
%>
<%@ include file="/vistas/comun/header.jsp" %>
<%@ include file="/vistas/comun/flash.jsp" %>

<div class="row justify-content-center">
    <div class="col-12 col-sm-10 col-md-6 col-lg-4">
        <div class="card shadow-sm">
            <div class="card-body">
                <h1 class="h4 mb-3 text-center">Iniciar sesión</h1>

                <% String error = (String) request.getAttribute("error"); if (error != null) { %>
                <div class="alert alert-danger"><%=error%></div>
                <% } %>

                <form method="post" action="<%=request.getContextPath()%>/auth/login" novalidate>
                    <div class="mb-3">
                        <label class="form-label">Correo</label>
                        <input class="form-control" type="email" name="email" required
                               value="<%= request.getParameter("email")!=null?request.getParameter("email"):"" %>">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Contraseña</label>
                        <input class="form-control" type="password" name="password" required>
                    </div>
                    <button class="btn btn-primary w-100" type="submit">Entrar</button>
                </form>

                <p class="mt-3 text-center">
                    ¿No tienes cuenta? <a href="<%=request.getContextPath()%>/auth/registro">Crear cuenta</a>
                </p>
            </div>
        </div>
    </div>
</div>

<%@ include file="/vistas/comun/footer.jsp" %>
