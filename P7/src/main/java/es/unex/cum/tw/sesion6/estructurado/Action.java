package es.unex.cum.tw.sesion6.estructurado;

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
@WebServlet(name = "Action", urlPatterns = { "/Estructurado/Action" })
public class Action extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Boolean autenticado = true;
		HttpSession s = request.getSession();
		Boolean auth = (Boolean) s.getAttribute("Autenticado");
		String action = (String) s.getAttribute("accion");

		if (auth) {
			if (action.equals("Anadir")) {
				RequestDispatcher rd = request.getRequestDispatcher("/Anadir");
				rd.forward(request, response);
			} else if (action.equals("Listar")) {
				RequestDispatcher rd = request.getRequestDispatcher("/Listar");
				rd.forward(request, response);
			}
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("../Error.html");
			rd.forward(request, response);
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
