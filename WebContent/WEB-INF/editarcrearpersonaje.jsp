<%@page import="business.entities.Personaje"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Personaje personajeCreandoEditando = (Personaje)session.getAttribute("personaje");
	String error = (String)session.getAttribute("error");
	String personajeGuardado = (String)session.getAttribute("personajeguardado");
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

    <title>Combate por Turnos - Editando Personaje</title>

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
              <li><a href="adminpersonajes">Administración de personajes</a></li>
              <li><a href="combate">Combate</a></li>
              <li><a href="seleccionarpersonajes">Nuevo combate</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

      <% if (error != null) { %>
      <div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
        <%= error %>.
	  </div>
      <%
         }
         if (personajeCreandoEditando != null) { %>
         <% if (personajeGuardado != null) { %>
         <div class="alert alert-success alert-dismissible" role="alert">
           <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
           <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
           <span class="sr-only">Success:</span>
           Los datos del personaje han sido guardados correctamente.
         </div>
         <% } %>
      <form class="form-signin" name="signin" action="editarcrearpersonaje" method="post">
        <h2 class="form-signin-heading">Edite su personaje</h2>
        <label>Nombre</label>
        <input name="nombre" id="txtNombre" class="form-control"
          value="<%= (personajeCreandoEditando.getNombre() != null) ? personajeCreandoEditando.getNombre() : "Nombre" %>"
        >
        <label>Vida</label>
        <input name="vida" id="txtVida" class="form-control"
          value="<%= personajeCreandoEditando.getVida() %>"
        >
        <label>Energía</label>
        <input name="energia" id="txtEnergia" class="form-control"
          value="<%= personajeCreandoEditando.getEnergia() %>"
        >
        <label>Defensa</label>
        <input name="defensa" id="txtDefensa" class="form-control"
          value="<%= personajeCreandoEditando.getDefensa() %>"
        >
        <label>Evasión</label>
        <input name="evasion" id="txtEvasion" class="form-control"
          value="<%= personajeCreandoEditando.getEvasion() %>"
        >
        <button class="btn btn-lg btn-primary btn-block" type="submit">Guardar</button>
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