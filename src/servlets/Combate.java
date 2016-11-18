package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ApplicationException;
import business.entities.Personaje;
import business.logic.CtrlABMCPersonaje;
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idPer1 = Integer.parseInt(request.getParameter("personaje1"));
		int idPer2 = Integer.parseInt(request.getParameter("personaje2"));
		if (idPer1 != idPer2) {
			Personaje per1 = new Personaje();
			Personaje per2 = new Personaje();
			per1.setId(idPer1);
			per2.setId(idPer2);
			CtrlABMCPersonaje controlABMC = new CtrlABMCPersonaje();
			try {
				per1 = controlABMC.getByCod(per1);
				per2 = controlABMC.getByCod(per2);
				CtrlCombate controladorCombate = new CtrlCombate(per1, per2);
				request.getSession().setAttribute("controlador", controladorCombate);
			} catch (ApplicationException e) {
				request.getSession().setAttribute("error", e.getMessage());
			}
		}
		else {
			request.getSession().setAttribute("error", "Los personajes seleccionados deben ser distintos");
		}
		request.getRequestDispatcher("/WEB-INF/combate.jsp").forward(request, response);
	}
}
