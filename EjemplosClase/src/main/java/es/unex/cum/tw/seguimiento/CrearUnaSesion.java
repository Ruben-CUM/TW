package es.unex.cum.tw.seguimiento;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CrearUnaSesion")
public class CrearUnaSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("text/plain");

		// Crear o recuperar la sesión
		HttpSession sesion = request.getSession();
		pw.println("La sesión acaba de ser creada:" + sesion.isNew());
		// Lectura de los atributos presentes:
		String nombre = (String) sesion.getAttribute("nombre");
		if (nombre != null) {
			pw.println("El atributo nombre = " + nombre);
		} else {
			pw.println("El atributo nombre no existe ");
		}
		String apellido = (String) sesion.getAttribute("apellido");
		if (apellido != null) {
			pw.println("El atributo apellido = " + apellido);
		} else {
			pw.println("El atributo apellido no existe ");
		}
		// Escritura de atributos:
		sesion.setAttribute("nombre", "prueba");
		sesion.setAttribute("apellido", "prueba");
		// Definición de un enlace presente en la página
		String url = request.getContextPath() + "/ruta";
		// Reescritura del enlace si el cliente no acepta las cookies
		String urlEncode = response.encodeURL(url);
		pw.println(urlEncode);
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
