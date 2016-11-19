package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ApplicationException;
import business.entities.BusinessEntity.States;
import business.entities.Personaje;
import business.logic.CtrlABMCPersonaje;

/**
 * Servlet implementation class EditarCrearPersonaje
 */
@WebServlet("/editarcrearpersonaje")
public class EditarCrearPersonaje extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarCrearPersonaje() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Personaje per = getPersonaje(request.getSession());
		request.getSession().setAttribute("personajeguardado", null);
		setError(request.getSession(), null);
		if (getPersonaje(request.getSession()) == null) {
			per = new Personaje();
			per.setState(States.NEW);
			setPersonaje(request.getSession(), per);
		}
		else {
			per.setState(States.MODIFIED);
		}
		request.getRequestDispatcher("/WEB-INF/editarcrearpersonaje.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Personaje personaje = getPersonaje(request.getSession());
		request.getSession().setAttribute("personajeguardado", null);
		setError(request.getSession(), null);
		if (personaje != null) {
			try {
				personaje.setNombre(request.getParameter("nombre"));
				personaje.setVida(Integer.parseInt(request.getParameter("vida")));
				personaje.setEnergia(Integer.parseInt(request.getParameter("energia")));
				personaje.setDefensa(Integer.parseInt(request.getParameter("defensa")));
				personaje.setEvasion(Integer.parseInt(request.getParameter("evasion")));
				if (personaje.isValido()) {
					new CtrlABMCPersonaje().save(personaje);
					request.getSession().setAttribute("personajeguardado", "ok");
				}
				else {
					setError(request.getSession(), "Los valores ingresados superan el máximo permitido");
				}
			}
			catch (NumberFormatException e) {
				setError(request.getSession(), "Los puntos ingresados deben ser enteros");
			} catch (ApplicationException e) {
				setError(request.getSession(), e.getMessage());
			}
			request.getRequestDispatcher("/WEB-INF/editarcrearpersonaje.jsp").forward(request, response);
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
