package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ApplicationException;
import business.logic.CtrlABMCPersonaje;

/**
 * Servlet implementation class InicioCombate
 */
@WebServlet("/iniciocombate")
public class InicioCombate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InicioCombate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CtrlABMCPersonaje control = new CtrlABMCPersonaje();
		try {
			request.getSession().setAttribute("listaPersonajes", control.getAll());
		} catch (ApplicationException e) {
			request.getSession().setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/seleccionarpersonaje.jsp").forward(request, response);
	}

}
