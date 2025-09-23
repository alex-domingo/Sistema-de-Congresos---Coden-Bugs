<%@ page contentType="text/html; charset=UTF-8" %>
<%
  request.setAttribute("tituloPagina", "Crear cuenta");
%>
<%@ include file="/vistas/comun/header.jsp" %>
<%@ include file="/vistas/comun/flash.jsp" %>

<div class="row justify-content-center">
    <div class="col-12 col-lg-8">
        <div class="card shadow-sm">
            <div class="card-body">
                <h1 class="h4 mb-3">Crear cuenta (Participante)</h1>

                <% String error = (String) request.getAttribute("error"); if (error != null) { %>
                <div class="alert alert-danger"><%= error %></div>
                <% } %>

                <form method="post" action="<%=request.getContextPath()%>/auth/registro" enctype="multipart/form-data" novalidate>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Nombre completo *</label>
                            <input type="text" name="nombre_completo" required class="form-control"
                                   value="<%= request.getParameter("nombre_completo")!=null?request.getParameter("nombre_completo"):"" %>">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Organización</label>
                            <input type="text" name="organizacion" class="form-control"
                                   value="<%= request.getParameter("organizacion")!=null?request.getParameter("organizacion"):"" %>">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Correo electrónico *</label>
                            <input type="email" name="email" required class="form-control"
                                   value="<%= request.getParameter("email")!=null?request.getParameter("email"):"" %>">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Teléfono</label>
                            <input type="text" name="telefono" class="form-control"
                                   value="<%= request.getParameter("telefono")!=null?request.getParameter("telefono"):"" %>">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">No. Identificación *</label>
                            <input type="text" name="identificacion" required class="form-control"
                                   value="<%= request.getParameter("identificacion")!=null?request.getParameter("identificacion"):"" %>">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Foto (opcional, máx. 5MB)</label>
                            <input type="file" name="foto" accept="image/*" class="form-control">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Contraseña *</label>
                            <input type="password" name="password" required class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Repetir contraseña *</label>
                            <input type="password" name="password2" required class="form-control">
                        </div>
                    </div>

                    <div class="d-flex justify-content-end gap-2 mt-4">
                        <a href="<%=request.getContextPath()%>/auth/login" class="btn btn-outline-secondary">Cancelar</a>
                        <button class="btn btn-primary" type="submit">Crear cuenta</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/vistas/comun/footer.jsp" %>
