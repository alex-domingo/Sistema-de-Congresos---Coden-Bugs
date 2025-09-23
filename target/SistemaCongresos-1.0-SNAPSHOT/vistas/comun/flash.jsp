<%
  String ok=(String)session.getAttribute("flash_ok");
  String err=(String)session.getAttribute("flash_error");
  if (ok!=null) { session.removeAttribute("flash_ok"); %>
<div class="alert alert-success"><%= ok %></div>
<%}
  if (err!=null) { session.removeAttribute("flash_error"); %>
<div class="alert alert-danger"><%= err %></div>
<%}%>
