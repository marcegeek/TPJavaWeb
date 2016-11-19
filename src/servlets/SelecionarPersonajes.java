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
import business.logic.CtrlABMCPersonaje;

/**
 * Servlet implementation class InicioCombate
 */
@WebServlet("/seleccionarpersonajes")
public class SelecionarPersonajes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelecionarPersonajes() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		int idPer1 = Integer.parseInt(request.getParameter("personaje1"));
    		int idPer2 = Integer.parseInt(request.getParameter("personaje2"));
    		Personaje per1 = new Personaje();
    		Personaje per2 = new Personaje();
    		per1.setId(idPer1);
    		per2.setId(idPer2);
    		CtrlABMCPersonaje controlABMC = new CtrlABMCPersonaje();
    		per1 = controlABMC.getByCod(per1);
    		per2 = controlABMC.getByCod(per2);
    		request.getSession().setAttribute("personaje1",per1);
    		request.getSession().setAttribute("personaje2",per2);
    		setError(request.getSession(), null);
    		response.sendRedirect("combate");
    	} catch (Exception e) {
    		setError(request.getSession(), "Error inesperado, consulte al administrador del sistema");
    		doGet(request, response);
    	}
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CtrlABMCPersonaje control = new CtrlABMCPersonaje();
		try {
			request.getSession().setAttribute("listaPersonajes", control.getAll());
			setError(request.getSession(), null);
		} catch (ApplicationException e) {
			setError(request.getSession(), e.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/seleccionarpersonajes.jsp").forward(request, response);
	}

	protected void setError(HttpSession session, String error) {
		session.setAttribute("error", error);
	}
}
