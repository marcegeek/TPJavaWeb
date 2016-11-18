<%@ page import="java.util.ArrayList" %>
<%@ page import="business.entities.Personaje" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	ArrayList<Personaje> list;
	String error = "";
	if (session.getAttribute("mensajeError") instanceof String)
		error = (String)session.getAttribute("error"); 
	if (session.getAttribute("listaPersonajes") instanceof ArrayList)
		list = (ArrayList<Personaje>) session.getAttribute("listaPersonajes"); 
	else
		list = null;
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">
    <title>Elecci&oacute;n de personajes</title>

    <!-- Bootstrap core CSS -->
    <link href="style/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="style/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style/css/navbar.css" rel="stylesheet">
    <link href="style/css/bootstrap-select.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

  </head>
  <body>

<div class="container">
<div class="jumbotron">
<form method="get">
Elecci&oacute;n de personaje!<br>
<select class="selectpicker" id="personajeIzq" name="personajeIzq">
<% for (Personaje per : list) { %>
<option value="<%= per.getId() %>"> <%= per.getNombre() %> </option>
<% } %>
</select>
<select class="selectpicker" id="personajeDer" name="personajeDer">
	<% for (Personaje per : list) { %>
	<option value="<%= per.getId() %>"> <%= per.getNombre() %> </option>
	<% } %>
</select><br>
<input type="submit" value="Comenzar!" id="btnComenzar" />
<div style="color: #f00; "><%= error %></div>
</form>
</div>
</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="style/js/jquery.min.js"><\/script>')</script>
    <script src="style/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="style/js/ie10-viewport-bug-workaround.js"></script>

    <script src="style/js/bootstrap-select.min.js"></script>
</body>
</html>