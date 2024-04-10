package es.unex.cum.tw.sesion6.basico;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ListarBasico", urlPatterns = { "/Basico/Listar" })
public class Listar extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s = request.getSession();
		String user = (String) s.getAttribute("user");
		if (user != null) {
			ArrayList<Integer> l = (ArrayList<Integer>) s.getAttribute("regalos");
			for (int j = 0; j < l.size(); j++) {
				response.getWriter().println("Valor:" + l.get(j));
			}
		} else {
			response.getWriter().append("No estas autenticado");
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}