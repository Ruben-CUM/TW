package es.unex.cum.tw;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Ejemplo")
public class Ejemplo extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ejemplo() {
		super();
	}

	public void init() throws ServletException {
		System.out.println("Starting servlet");
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Call to Service method");
		super.service(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Llamada al método doGet del servlet");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Llamada al método doPost del servlet");
		doGet(request, response);
	}
}
