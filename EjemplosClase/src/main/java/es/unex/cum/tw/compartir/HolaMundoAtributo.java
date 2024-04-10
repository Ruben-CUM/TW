/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unex.cum.tw.compartir;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Ejemplo HolaMundo el Servlet
 */
@WebServlet(name="HolaMundoAtributo",urlPatterns={"/Compartir/HolaMundoAtributo"})
public class HolaMundoAtributo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Integer x= 10;
		req.setAttribute("valor",x);
		RequestDispatcher rd =req.getRequestDispatcher ("/Compartir/Atributo") ;
		rd.forward(req,res) ;
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
