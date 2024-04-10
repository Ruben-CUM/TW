package es.unex.cum.tw.reyesmagos;

import java.io.IOException;
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

/**
 * Ejemplo de uso de Pool de conexiones con una BD con servlet. Importante se
 * carga el pool el init() del servlet y luego se utiliza de forma sincronizada
 * en el processRequest Es necesario configurar el fichero context.xml dentro de
 * META-INF
 * 
 */
@WebServlet("/Alta")
public class Alta extends HttpServlet {

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
		String usuario = req.getParameter("username");
		ResultSet resultados = null;
		PreparedStatement sentencia = null;
		if (usuario == null)
			res.sendRedirect("Error.html");
		try {
			/*
			 * Statement sent= conexion.createStatement(); synchronized (sent) { resultados
			 * = sent.executeQuery("SELECT * FROM usuarios where username='" + usuario +
			 * "'"); }
			 */
			String query = "SELECT * FROM usuarios where username=?";
			sentencia = conexion.prepareStatement(query);
			sentencia.setString(1, usuario);
			resultados = sentencia.executeQuery();
			if (resultados.next() == true) {
				res.sendRedirect("Error.html");
			} else {
				resultados.close();
				sentencia.close();
				/*
				 * synchronized (sentencia) {
				 * 
				 * query = "INSERT INTO usuarios (nombre,apellidos,email,username,password) " +
				 * "VALUES ('" + req.getParameter("nombre") + "','" +
				 * req.getParameter("apellidos") + "','" + req.getParameter("email") + "','" +
				 * req.getParameter("username") + "','" + req.getParameter("password") + "')";
				 * sentencia.executeUpdate(query);
				 */
				query = "INSERT INTO usuarios (nombre,apellidos,email,username,password) " + "VALUES (?,?,?,?,?)";
				sentencia = conexion.prepareStatement(query);
				synchronized (sentencia) {
					sentencia.setString(1, req.getParameter("nombre"));
					sentencia.setString(2, req.getParameter("apellidos"));
					sentencia.setString(3, req.getParameter("email"));
					sentencia.setString(4, req.getParameter("username"));
					sentencia.setString(5, req.getParameter("password"));
					sentencia.executeUpdate();
				}
				res.sendRedirect("Inicio.html");
			}
		} catch (SQLException e2) {
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
