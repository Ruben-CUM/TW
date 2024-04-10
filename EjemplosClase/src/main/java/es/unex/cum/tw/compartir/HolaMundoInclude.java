/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unex.cum.tw.compartir;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Ejemplo HolaMundo el Servlet
 */
@WebServlet(name="HolaMundoInclude",urlPatterns={"/Compartir/HolaMundoInclude"})
public class HolaMundoInclude extends HttpServlet {

	private static final long serialVersionUID = 1L;

	

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw=  res.getWriter();
	    pw.write("<b>Primer servlet</b> <br>");
		RequestDispatcher rd =req.getRequestDispatcher ("/Compartir/Include") ;
		rd.include(req,res) ;
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
