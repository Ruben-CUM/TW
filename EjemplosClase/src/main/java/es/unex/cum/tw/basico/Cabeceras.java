package es.unex.cum.tw.basico;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Cabeceras
 * Muestra como procesar las cabeceras recibidas en el request
 */
public class Cabeceras extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Cabeceras() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet CabecerasServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Cabeceras: </h1>");
			out.println("<ul>");
			Enumeration<String> nombresDeCabeceras = request.getHeaderNames();
			while (nombresDeCabeceras.hasMoreElements()) {
				String cabecera = nombresDeCabeceras.nextElement();
				out.println("<li><b>" + cabecera + ": </b>"
						+ request.getHeader(cabecera) + "</li>");
			}
			out.println("</ul>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
