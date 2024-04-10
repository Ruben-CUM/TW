package es.unex.cum.tw.reyesmagos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Configuracion
 */
@WebServlet("/AddCarta")
public class AddCarta extends HttpServlet {
	private Connection conexion = null;
	private DataSource servicioConexiones = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			// Recuperar el contexto inicial
			// Referencia al servicio de conexiones
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			servicioConexiones = (DataSource) envContext.lookup("jdbc/testdb");
			conexion = servicioConexiones.getConnection();

		} catch (Exception e) {
			throw new ServletException("Imposible recuperar java:comp/env/jdbc/testdb", e);
		}
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ResultSet resultados = null;
		PreparedStatement sentencia = null;

		try {
			/*
			 * Statement sent= conexion.createStatement(); synchronized (sent) { resultados
			 * = sent.executeQuery("SELECT * FROM usuarios where username='" + usuario +
			 * "'"); }
			 */

			/*
			 * synchronized (sentencia) {
			 * 
			 * query = "INSERT INTO usuarios (nombre,apellidos,email,username,password) " +
			 * "VALUES ('" + req.getParameter("nombre") + "','" +
			 * req.getParameter("apellidos") + "','" + req.getParameter("email") + "','" +
			 * req.getParameter("username") + "','" + req.getParameter("password") + "')";
			 * sentencia.executeUpdate(query);
			 */
			String query = "INSERT INTO carta (regalo,idUsuario) " + "VALUES (?,?)";
			sentencia = conexion.prepareStatement(query);
			HttpSession session = req.getSession(true);
			int idUsuario = (int)session.getAttribute("id");
			synchronized (sentencia) {
				if (req.getParameter("regalo") == null) {
					res.sendRedirect("Error.html");
				}
				sentencia.setString(1, req.getParameter("regalo"));
				sentencia.setInt(2, idUsuario);
				sentencia.executeUpdate();
			}
			

		} catch (

		SQLException e2) {
			Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, "No se pudo cerrar el Resulset", e2);
		} finally {
			if (sentencia != null) { // Se cierra resultSet
				try {
					sentencia.close();
				} catch (SQLException ex) {
					Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}
		res.sendRedirect("Introducir.html");
	}

	@Override
	public void destroy() {
		try {
			conexion.close();
		} catch (SQLException ex) {
			Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, "No se pudo cerrar el objeto Statement", ex);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
