package es.unex.cum.tw;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Auth")
public class Autenticacion extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre = null;
	private String password = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		nombre = config.getInitParameter("usuario");
		password = config.getInitParameter("password");
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html><head><title>");
		out.println("Autenticacion</title><body>");
		String nombreRecibido = req.getParameter("user");
		String passRecibido = req.getParameter("password");
		if (nombreRecibido == null || passRecibido == null) {
			out.println("<h1>Datos no válidos<h1>");
		} else if (nombreRecibido.equals(nombre) && passRecibido.equals(password)) {
			out.println("<h1>Usuario autenticado<h1>");
		} else
			out.println("<h1>Usuario no autenticado<h1>");
		out.println("</body></html>");
		out.close();
	}
}