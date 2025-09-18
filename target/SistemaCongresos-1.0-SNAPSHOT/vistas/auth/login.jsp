<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Iniciar sesión</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/recursos/css/estilos.css">
    </head>
    <body>
        <h2>Iniciar sesión</h2>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
        <div style="color: red;"><%= error %></div>
        <% } %>

        <form method="post" action="<%=request.getContextPath()%>/auth/login">
            <div>
                <label>Email</label><br>
                <input type="email" name="email" required />
            </div>
            <div>
                <label>Contraseña</label><br>
                <input type="password" name="password" required />
            </div>
            <button type="submit">Entrar</button>
        </form>

    </body>
</html>
