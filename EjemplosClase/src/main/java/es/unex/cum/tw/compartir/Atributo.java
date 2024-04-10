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
@WebServlet(name="Atributo",urlPatterns={"/Compartir/Atributo"})
public class Atributo extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Integer valor=(Integer) req.getAttribute("valor");		
		PrintWriter out = res.getWriter();
		out.println(valor);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
