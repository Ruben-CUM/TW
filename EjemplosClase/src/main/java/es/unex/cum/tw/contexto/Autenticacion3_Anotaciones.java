package es.unex.cum.tw.contexto;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="Autenticacion3_Anotaciones",value={"/Autenticacion3_Anotaciones"},initParams={@WebInitParam(name="ficheroUsuarios", value = "WEB-INF/ficheroUsuarios.prop")})

public class Autenticacion3_Anotaciones extends HttpServlet {
	private String user = null;
	private String password = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = getServletContext();
		String ficheroUsuarios = context.getInitParameter("ficheroUsuarios");
		InputStream is = context.getResourceAsStream(ficheroUsuarios);
		Properties ficheroPropiedades = new Properties();
		try {
			ficheroPropiedades.load(is);
			user = ficheroPropiedades.getProperty("UserAdmin");
			password = ficheroPropiedades.getProperty("PasswordAdmin");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		} else if (nombreRecibido.equals(user) && passRecibido.equals(password)) {
			out.println("<h1>Usuario autenticado<h1>");
		} else
			out.println("<h1>Usuario no autenticado<h1>");
		out.println("</body></html>");
		out.close();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		processRequest(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		processRequest(req, res);
	}
}
