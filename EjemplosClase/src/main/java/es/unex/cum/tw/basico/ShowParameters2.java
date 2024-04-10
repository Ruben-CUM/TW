/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unex.cum.tw.basico;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Ejemplo para capturar parámetros cuando se desconocen. Se recorre como
 * enumeración
 */
public class ShowParameters2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out;
		String title = "Leyendo todos los parámetros";
		response.setContentType("text/html");
		out = response.getWriter();
		out.println("<html><head><title>");
		out.println(title);
		out.println("</title></head><body>");
		out.println("<h1 style='align: center'>" + title + "</h1>");
		out.println("<table border='1' align='center'>");
		out.println("<tr><th>Nombre de parámetro</th><th>Valores de parámetro</th></tr>");
		Enumeration nombresDeParam = request.getParameterNames();
		while (nombresDeParam.hasMoreElements()) {
			String nombreParam = (String) nombresDeParam.nextElement();
			out.println("<tr><td>" + nombreParam + "</td>");
			out.println("<td>");
			String[] valoresDeParam = request.getParameterValues(nombreParam);
			if (valoresDeParam.length == 1) {
				String valorParam = valoresDeParam[0];
				if (valorParam.length() == 0) {
					out.print("<i>No existe valor</i>");
				} else {
					out.print(valorParam);
				}
			} else {
				out.println("<ul>");
				for (int i = 0; i < valoresDeParam.length; i++) {
					out.println("<li>" + valoresDeParam[i]);
				}
				out.println("</ul>");
			}
			out.println("</td>");
		}
		out.println("</table>");
		out.println("</body></html>");
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
