package es.unex.cum.tw.reyesmagos;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Sesiones
 */
@WebServlet("/Action")
public class Action extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("accion");

		HttpSession s = request.getSession();
		String id= (String) s.getAttribute("id");
		try {
			if (id != null) {
				if (action.equals("eliminarCarta")) {

				}
				if(action.equals("addCarta")) {
					RequestDispatcher rd = request.getRequestDispatcher("/AddCarta");
					rd.forward(request, response);
					
				}
			} else {
				response.sendRedirect("error.html");
			}

		} finally {

		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}


}
