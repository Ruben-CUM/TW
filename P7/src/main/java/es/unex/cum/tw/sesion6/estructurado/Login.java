package es.unex.cum.tw.sesion6.estructurado;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Configuracion
 */
@WebServlet(name = "Login", urlPatterns = { "/Estructurado/Login" })
public class Login extends HttpServlet {

	public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		PrintWriter out = res.getWriter();
		String nombreRecibido = req.getParameter("user");
		String passRecibido = req.getParameter("password");
		if (nombreRecibido == null || passRecibido == null) {
			out.println("Datos no válidos");
		} else if (nombreRecibido.equals("admin") && passRecibido.equals("admin")) {
			out.println("Usuario autenticado");
			Boolean autenticado = true;
			HttpSession s = req.getSession();
			s.setAttribute("Autenticado", autenticado);
			s.setAttribute("user", "admin");
			ArrayList<String> l = (ArrayList<String>) s.getAttribute("lista");
			if (l == null)
				l = new ArrayList<String>();
			s.setAttribute("regalos", l);
			RequestDispatcher rd = req.getRequestDispatcher("../Introducir.html");
			rd.forward(req, res);
		} else {
			out.println("Usuario no autenticado");
			RequestDispatcher rd = req.getRequestDispatcher("../Error.html");
			rd.forward(req, res);
		}
		out.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(request, res);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(request, res);
	}

}
