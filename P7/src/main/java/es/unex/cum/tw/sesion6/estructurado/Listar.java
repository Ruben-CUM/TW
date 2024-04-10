package es.unex.cum.tw.sesion6.estructurado;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Listar
 */
@WebServlet(name = "Listar", urlPatterns = { "/Estructurado/Listar" })
public class Listar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession s = request.getSession();
		Boolean auth = (Boolean) s.getAttribute("Autenticado");
		String action = (String) s.getAttribute("Accion");
		
		if(auth) {
			List<String> regalos = (List<String>) s.getAttribute("regalos");
			if (regalos == null) {
				regalos = new ArrayList<String>();
				s.setAttribute("regalos", regalos);
				response.getWriter().append("Lista vacia");
			}else {
				for(String str : regalos) {
					//TODO Mostrar regalos
				}
			}
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("../Error.html");
			rd.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
