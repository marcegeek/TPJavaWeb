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
import business.entities.BusinessEntity.States;
import business.logic.CtrlABMCPersonaje;

/**
 * Servlet implementation class EliminarPersonaje
 */
@WebServlet("/eliminarpersonaje")
public class EliminarPersonaje extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarPersonaje() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Personaje per = getPersonaje(request.getSession());
		request.getSession().setAttribute("personajeeliminado", null);
		if (getPersonaje(request.getSession()) != null) {
			per.setState(States.DELETED);
			request.getRequestDispatcher("/WEB-INF/eliminarpersonaje.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("adminpersonajes");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Personaje personaje = getPersonaje(request.getSession());
		setError(request.getSession(), null);
		request.getSession().setAttribute("personajeeliminado", null);
		if (personaje != null) {
			try {
				if (request.getParameter("si") != null) {
					new CtrlABMCPersonaje().save(personaje);
					request.getSession().setAttribute("personajeeliminado", "ok");
				}
				else {
					response.sendRedirect("adminpersonajes");
					return;
				}
			}
			catch (ApplicationException e) {
				setError(request.getSession(), e.getMessage());
			}
			request.getRequestDispatcher("/WEB-INF/eliminarpersonaje.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("adminpersonajes");
		}
	}

	protected Personaje getPersonaje(HttpSession session) {
		return (Personaje)session.getAttribute("personaje");
	}

	protected void setPersonaje(HttpSession session, Personaje personaje) {
		session.setAttribute("personaje", personaje);
	}

	protected void setError(HttpSession session, String error) {
		session.setAttribute("error", error);
	}
}
