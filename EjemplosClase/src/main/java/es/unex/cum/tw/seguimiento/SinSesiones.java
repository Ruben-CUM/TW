package es.unex.cum.tw.seguimiento;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Ejemplo HolaMundo el Servlet
 */
@WebServlet(name = "SinSesiones", urlPatterns = { "/Seguimiento/SinSesiones" })
public class SinSesiones extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String valor = request.getParameter("num");
		if (valor != null) {
			Integer i = new Integer(Integer.parseInt(valor));
			
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(i);
			response.getWriter().println("Hay " + l.size() + " datos");
			// Definición de un enlace presente en la página
			String url = request.getContextPath() + "/ruta";
			// Reescritura del enlace si el cliente no acepta las cookies
			String urlEncode = response.encodeURL(url);
			response.getWriter().println(urlEncode);
			response.getWriter().close();
			
		} else {
			response.getWriter().append("Faltan datos");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public String getServletInfo() {
		return "Short description";
	}

}
