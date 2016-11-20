<%@page import="java.util.ArrayList"%>
<%@page import="business.logic.CtrlCombate"%>
<%@page import="business.entities.Personaje"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	CtrlCombate controlador = (CtrlCombate)session.getAttribute("controladorCombate");
	String error = (String)session.getAttribute("error");
%>

<%!
	private String puntosDeN(int val1, int val2) {
		return String.valueOf(val1) + " / " + String.valueOf(val2);
	}

    private void setControladorCombate(HttpSession session, CtrlCombate controlador) {
    	session.setAttribute("controladorCombate", controlador);
    }
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

    <title>Combate por Turnos - Combate</title>

    <!-- Bootstrap core CSS -->
    <link href="style/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="style/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style/css/navbar.css" rel="stylesheet">
    <link href="style/css/start.css" rel="stylesheet">
    <link href="style/css/starter-template.css" rel="stylesheet">

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
              <li><a href="seleccionarpersonajes">Nuevo combate</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

      <% if (error != null) { %>
      <div class="alert alert-danger <% if (controlador != null) { %>alert-dismissible" <% } %>role="alert">
        <% if (controlador != null) { %>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <% } %>
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
        <%= error %>.
        <% if (controlador == null) { %>
        <a href="seleccionarpersonajes" class="alert-link">Volver a la selección de personajes</a>.
        <% } %>
	  </div>
      <%
         }
         if (controlador != null) {
      %>
      <div class="starter-template">
        <h1>Combate: <%= controlador.getPers1().getNombre() %> vs. <%= controlador.getPers2().getNombre() %></h1>
        <div class="row">
          <div class="col-md-6">
            <h2><%= controlador.getPers1().getNombre() %></h2>
            <p>Vida: <%= puntosDeN(controlador.getPers1().getVidaActual(), controlador.getPers1().getVida()) %></p>
            <p>Energía: <%= puntosDeN(controlador.getPers1().getEnergiaActual(), controlador.getPers1().getEnergia()) %></p>
          </div>
          <div class="col-md-6">
            <h2><%= controlador.getPers2().getNombre() %></h2>
            <p>Vida: <%= puntosDeN(controlador.getPers2().getVidaActual(), controlador.getPers2().getVida()) %></p>
            <p>Energía: <%= puntosDeN(controlador.getPers2().getEnergiaActual(), controlador.getPers2().getEnergia()) %></p>
          </div>
        </div>
      	<% if (!controlador.isCombateFinalizado()) { %>
      	<h3>Registro de sucesos del combate</h3>
      	<textarea style="width: 75%;" rows="7" cols="7"><%= controlador.getSucesosCombate() %></textarea>
      	<% }
      	   else {
      		   session.setAttribute("personaje", controlador.getGanador());
      	%>
      	<h2>El ganador del combate es:</h2>
      	<p><%= controlador.getGanador().getNombre() %></p>
      	<p>
          <a class="btn btn-lg btn-primary" href="editarcrearpersonaje" role="button">Asignar los <%= controlador.getCombate().getPuntos() %> puntos ganados</a>
        </p>
      	<%
      	       /*
      	        * elimino del controlador de la sesión,
      	        * ya que terminó el combate, si intenta
      	        * volver al mismo lo enviará a la selección
      	        * de personajes para un nuevo combate
      	        */
      	       setControladorCombate(session, null);
      	   }
      	%>
      </div>

      <%   if (!controlador.isCombateFinalizado()) { %>
      <form class="form-signin" name="signin" action="combate" method="post">
        <h2 class="form-signin-heading">Turno de <%= controlador.getTurno().getNombre() %></h2>
        <label for="txtPuntosAtaque" class="sr-only">Puntos de ataque</label>
        <input type="number" min="0" max="<%= controlador.getTurno().getEnergiaActual() %>" name="puntosAtaque" id="txtPuntosAtaque" class="form-control-small" placeholder="Puntos de ataque">
        <button class="btn btn-lg btn-primary" name="atacar" id="btnAtacar" disabled="disabled">Atacar</button>
        <button class="btn btn-lg btn-primary" name="defender" id="btnDefender">Defender</button>
        <button class="btn btn-default btn-lg" name="cancelar" id="btnCancelar">
          <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancelar combate
        </button>
      </form>
      <%
           }
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

    <%
    if (controlador != null && !controlador.isCombateFinalizado()) {
    %>
    <script type="text/javascript">
      function isInt(n) {
    	  return $.isNumeric(n) && n === parseInt(n, 10).toString();
      }
      $("#txtPuntosAtaque").keyup(function() {
    	  var txtPuntosAtaque = $(this);
    	  if (isInt(txtPuntosAtaque.val())) {
    		  $("#btnAtacar").prop("disabled",false);
    	  }
    	  else {
    		  $("#btnAtacar").prop("disabled",true);
    	  }
      })
    </script>
    <%
    }
    %>    
  </body>
</html>