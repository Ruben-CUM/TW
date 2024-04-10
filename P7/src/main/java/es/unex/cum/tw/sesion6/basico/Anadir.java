package es.unex.cum.tw.sesion6.basico;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AnadirBasico", urlPatterns = { "/Basico/Anadir" })
public class Anadir extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s = request.getSession();
		String si = (String) s.getAttribute("user");
		if (si != null) {
			String valor = request.getParameter("regalo");
			ArrayList<String> l = (ArrayList<String>) s.getAttribute("regalos");
			l.add(valor);			
			response.getWriter().append("Valor añadido correctamente");
		} else {
			response.getWriter().append("No estas autenticado");
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}