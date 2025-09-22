<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Crear cuenta</title>
        <style>
            body {
                font-family: sans-serif;
            }
            form div {
                margin-bottom: 10px;
            }
            label {
                font-weight: bold;
            }
            input[type="text"], input[type="email"], input[type="password"] {
                width: 280px;
            }
        </style>
    </head>
    <body>
        <h2>Crear cuenta (Participante)</h2>
        <p><a href="<%=request.getContextPath()%>/auth/login">← Volver al login</a></p>

        <%
          String error = (String) request.getAttribute("error");
          if (error != null) {
        %>
        <div style="color:red;"><%= error %></div>
        <%
          }
        %>

        <form method="post" action="<%=request.getContextPath()%>/auth/registro" enctype="multipart/form-data">
            <div>
                <label>Nombre completo *</label><br/>
                <input type="text" name="nombre_completo" required
                       value="<%= request.getParameter("nombre_completo")!=null?request.getParameter("nombre_completo"):"" %>"/>
            </div>

            <div>
                <label>Organización</label><br/>
                <input type="text" name="organizacion"
                       value="<%= request.getParameter("organizacion")!=null?request.getParameter("organizacion"):"" %>"/>
            </div>

            <div>
                <label>Correo electrónico *</label><br/>
                <input type="email" name="email" required
                       value="<%= request.getParameter("email")!=null?request.getParameter("email"):"" %>"/>
            </div>

            <div>
                <label>Teléfono</label><br/>
                <input type="text" name="telefono"
                       value="<%= request.getParameter("telefono")!=null?request.getParameter("telefono"):"" %>"/>
            </div>

            <div>
                <label>No. Identificación (puede contener letras) *</label><br/>
                <input type="text" name="identificacion" required
                       value="<%= request.getParameter("identificacion")!=null?request.getParameter("identificacion"):"" %>"/>
            </div>

            <div>
                <label>Foto (opcional, máx. 5MB)</label><br/>
                <input type="file" name="foto" accept="image/*"/>
            </div>

            <div>
                <label>Contraseña *</label><br/>
                <input type="password" name="password" required/>
            </div>

            <div>
                <label>Repetir contraseña *</label><br/>
                <input type="password" name="password2" required/>
            </div>

            <button type="submit">Crear cuenta</button>
        </form>
    </body>
</html>
