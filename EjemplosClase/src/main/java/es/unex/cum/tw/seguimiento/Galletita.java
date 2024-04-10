/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unex.cum.tw.seguimiento;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "Galletita", urlPatterns = { "/Seguimiento/Cookie" })
public class Galletita extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreNuevaCookie = request.getParameter("cookie");
		String valor = request.getParameter("valor");
		int duracion=10000;

		PrintWriter out = response.getWriter();
		try {
			out.println("El contenido de tu sesion en cookie es:");
			if (nombreNuevaCookie != null && valor != null && !nombreNuevaCookie.equals("")) {
				Cookie nuevaCookie = new Cookie(nombreNuevaCookie,valor);
				nuevaCookie.setMaxAge(duracion);
				response.addCookie(nuevaCookie);
				out.println(nuevaCookie.getName() + " con valor " + nuevaCookie.getValue() + " y  fecha de expiracion: "
						+ nuevaCookie.getMaxAge());
			}
			out.println("Mostramos todas las cookies");
			Cookie[] todasLasCookies = request.getCookies();
			if (todasLasCookies != null) {
				for (Cookie cookie : todasLasCookies) {
					out.println("Nombre: "+cookie.getName()+ " valor: "+cookie.getValue());
				}
			}
		} catch (Exception e) {

		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
