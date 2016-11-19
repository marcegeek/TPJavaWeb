package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ApplicationException;
import business.entities.Personaje;
import business.logic.CtrlABMCPersonaje;

/**
 * Servlet implementation class AdminPersonajes
 */
@WebServlet("/adminpersonajes")
public class AdminPersonajes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPersonajes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Personaje> listaPersonajes = new CtrlABMCPersonaje().getAll();
			request.getSession().setAttribute("listaPersonajes", listaPersonajes);
			setError(request.getSession(), null);
		} catch (ApplicationException e) {
			setError(request.getSession(), e.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/adminpersonajes.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("editar") != null || request.getParameter("eliminar") != null) {
			try {
				int id = Integer.parseInt(request.getParameter("personaje"));
				Personaje per = new Personaje();
				per.setId(id);
				per = new CtrlABMCPersonaje().getByCod(per);
				setPersonaje(request.getSession(),per);
				setError(request.getSession(), null);
			} catch (NumberFormatException e) {
				setError(request.getSession(),"Error inesperado, consulte al administrador del sistema");
			} catch (ApplicationException e) {
				setError(request.getSession(), e.getMessage());
			}
		}
		else if (request.getParameter("nuevo") != null) {
			setPersonaje(request.getSession(),null);
		}
		if (request.getParameter("editar") != null || request.getParameter("nuevo") != null) {
			response.sendRedirect("editarcrearpersonaje");
		}
		else if (request.getParameter("eliminar") != null) {
			response.sendRedirect("eliminarpersonaje");
		}
	}

	protected void setPersonaje(HttpSession session, Personaje personaje) {
		session.setAttribute("personaje", personaje);
	}

	protected void setError(HttpSession session, String error) {
		session.setAttribute("error", error);
	}
}
