package es.unex.cum.tw.sesion6.basico;

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

@WebServlet(name = "LoginBasico", urlPatterns = { "/Basico/Login" })
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void ProcessRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		String nombreRecibido = request.getParameter("user");
		String passRecibido = request.getParameter("password");
		if (nombreRecibido == null || passRecibido == null) {
			out.println("Datos no válidos");
		} else if (nombreRecibido.equals("admin") && passRecibido.equals("admin")) {
			out.println("Usuario autenticado");
			Boolean autenticado = true;
			HttpSession s = request.getSession();
			s.setAttribute("autenticado", autenticado);
			s.setAttribute("user", "admin");
			ArrayList<String> l = (ArrayList<String>) s.getAttribute("lista");
			if (l == null)
				l = new ArrayList<String>();
			s.setAttribute("regalos", l);
			RequestDispatcher rd = request.getRequestDispatcher("../Introducir");
			rd.forward(request, response);
		} else {
			out.println("Usuario no autenticado");
			RequestDispatcher rd = request.getRequestDispatcher("../Error");
			rd.forward(request, response);
		}
		out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessRequest(request, response);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ProcessRequest(req, res);
	}
}