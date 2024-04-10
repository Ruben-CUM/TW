/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unex.cum.tw.compartir;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Ejemplo HolaMundo el Servlet
 */
@WebServlet(name="Forward",urlPatterns={"/Compartir/Forward"})
public class Forward extends HttpServlet {

	private static final long serialVersionUID = 1L;

	

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet HolaMundo</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet HolaMundo</h1>");
			out.println("<h1>He realizado forward</h1>");
			out.println("</body>");
			out.println("</html>");

		} finally {
			out.close();
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
