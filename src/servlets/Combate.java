package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ApplicationException;
import business.entities.Personaje;
import business.logic.CtrlCombate;

/**
 * Servlet implementation class Combate
 */
@WebServlet("/combate")
public class Combate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Combate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setError(request.getSession(), null);
		if (getControlador(request.getSession()) == null) {
			Personaje per1 = (Personaje)request.getSession().getAttribute("personaje1");
			Personaje per2 = (Personaje)request.getSession().getAttribute("personaje2");
			if (per1 == null || per2 == null) {
				response.sendRedirect("seleccionarpersonajes");
				return;
			}
			CtrlCombate controladorCombate;
			try {
				controladorCombate = new CtrlCombate(per1, per2);
				setControlador(request.getSession(), controladorCombate);
				/*
				 * elimino los personajes de la sesión, ya no son necesarios,
				 * así al cancelar el combate, si intenta volver al mismo lo
				 * lleva a seleccionar personajes para un nuevo combate
				 */
				request.getSession().setAttribute("personaje1", null);
				request.getSession().setAttribute("personaje2", null);
			} catch (ApplicationException e) {
				setError(request.getSession(), e.getMessage());
			}
		}
		request.getRequestDispatcher("/WEB-INF/combate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CtrlCombate controlador = getControlador(request.getSession());
		setError(request.getSession(), null);
		if (controlador != null) {
			if (request.getParameter("atacar") != null) {
				try {
					controlador.atacar(Integer.parseInt(request.getParameter("puntosAtaque")));
				} catch (NumberFormatException e) {
					setError(request.getSession(), "Debe ingresar un número entero");
				} catch (ApplicationException e) {
					setError(request.getSession(), e.getMessage());
				}
			}
			else if (request.getParameter("defender") != null) {
				controlador.defender();
			}
			else if (request.getParameter("cancelar") != null) {
				setControlador(request.getSession(), null);
				response.sendRedirect("seleccionarpersonajes");
				return;
			}
			request.getRequestDispatcher("/WEB-INF/combate.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("seleccionarpersonajes");
		}
	}

	protected CtrlCombate getControlador(HttpSession session) {
		return (CtrlCombate)session.getAttribute("controladorCombate");
	}

	protected void setControlador(HttpSession session, CtrlCombate controlador) {
		session.setAttribute("controladorCombate", controlador);
	}

	protected void setError(HttpSession session, String error) {
		session.setAttribute("error", error);
	}
}
