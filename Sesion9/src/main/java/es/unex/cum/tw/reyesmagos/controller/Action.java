package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Configuracion
 */
@WebServlet(urlPatterns="/Restringido/Action")
public class Action extends HttpServlet {
	
	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
				String accion = req.getParameter("action");
		if (accion.contains("Producto")) {
			RequestDispatcher productos= req.getRequestDispatcher("/Restringido/ProductosController");
			productos.forward(req, res);
		} else if (accion.contains("InfoUsuario")) {
			RequestDispatcher userView= req.getRequestDispatcher("/UsuarioController");
			userView.forward(req, res);	
		} else if (accion.contains("Carta")) {
			req.getRequestDispatcher("/Restringido/CartaReyesController").forward(req, res);
		} else if (accion.contains("Usuario")) {
			req.getRequestDispatcher("../WEB-INF/Error.jsp?error=No hay acción de usuario").forward(req, res);
		} else {
			RequestDispatcher errorView= req.getRequestDispatcher("../WEB-INF/Error.jsp?error=No hay acción");
			errorView.forward(req, res);	
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
