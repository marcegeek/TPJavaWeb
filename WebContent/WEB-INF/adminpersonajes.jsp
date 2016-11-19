<%@page import="business.entities.Personaje"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<Personaje> listaPersonajes = listaPersonajes = (List<Personaje>) session.getAttribute("listaPersonajes");
	String error = (String)session.getAttribute("error");
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

    <title>Combate por Turnos - Administración de Personajes</title>

    <!-- Bootstrap core CSS -->
    <link href="style/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="style/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style/css/navbar.css" rel="stylesheet">
    <link href="style/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="style/css/start.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container">

      <!-- Static navbar -->
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.html">Turn Based Combat</a>
          </div>
          <div style="height: 1px;" aria-expanded="false" id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li class="active"><a href="index.html">Inicio</a></li>
              <li><a href="combate">Combate</a></li>
              <li><a href="seleccionarpersonajes">Nuevo combate</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

      <% if (error != null) { %>
      <div class="alert alert-danger" role="alert">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
        <%= error %>.
	  </div>
      <%
         }
         else {
      %>
      <form class="form-signin" name="signin" action="adminpersonajes" method="post">
        <h2 class="form-signin-heading">Administración de personajes</h2>
        <button class="btn btn-default btn-lg" name="nuevo" id="btnNuevo">
          <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Nuevo
        </button>
        <button class="btn btn-default btn-lg" name="editar" id="btnEditar">
          <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Editar
        </button>
        <button class="btn btn-default btn-lg" name="eliminar" id="btnEliminar">
          <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Eliminar
        </button>
        <select class="selectpicker" data-width="100%" id="personaje" name="personaje">
          <% for (Personaje per : listaPersonajes) { %>
          <option value="<%= per.getId() %>"> <%= per.getNombre() %> </option>
          <% } %>
        </select>
      </form>
      <%
         }
      %>

      <footer>&copy; 2016 Marcelo Castellano</footer>      

    </div> <!-- /container -->

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