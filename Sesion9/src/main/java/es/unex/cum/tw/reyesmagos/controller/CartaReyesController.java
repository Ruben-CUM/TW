package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Configuracion
 */
@WebServlet(urlPatterns = "/Restringido/CartaReyesController")
public class CartaReyesController extends HttpServlet {
	private Connection conexion = null;
	private DataSource servicioConexiones = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			// Recuperar el contexto inicial
			Context ctx = new InitialContext();
			// Referencia al servicio de conexiones
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			servicioConexiones = (DataSource) envContext.lookup("jdbc/testdb");
			conexion = servicioConexiones.getConnection();
		} catch (Exception e) {
			throw new ServletException("Imposible recuperar java:comp/env/jdbc/testdb", e);
		}
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// No hace falta por el filter
		/*
		 * HttpSession session = req.getSession(); String id = (String)
		 * session.getAttribute("id"); if (id != null && !id.equals("")) {
		 */
		String accion = req.getParameter("action");
		if (accion.equals("anadirCarta")) {
			anadirCarta(req, res);
		} else if (accion.equals("VerCarta")) {
			verCarta(req, res);
		} else if (accion.equals("eliminarCarta")) {
			eliminarCarta(req, res);
		} else {
			res.sendRedirect("WEB-INF/Principal.jsp?mensaje=No hay acción");
		}
		/*
		 * } else { res.sendRedirect("Error.jsp?error=ERROR: Problema con la sesión"); }
		 */
	}

	public void anadirCarta(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("Id");
		String idP = (String) req.getParameter("idP");
		ResultSet resultados = null;// Objeto para guardar los resultados
		try {
			Statement sentencia = conexion.createStatement();
			
			synchronized (sentencia) {
				// Añadimos productos
				String query = "insert into carta (idCliente, idProducto) VALUES ('" + id + "', '" + idP +"')";
				sentencia.executeUpdate(query);
			}
			req.setAttribute("mensaje", "Eliminado del carrito perfectamente");
			req.getRequestDispatcher("WEB-INF/" + "Principal.jsp").forward(req, res);

		} catch (SQLException e2) {
			res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(CartaReyesController.class.getName()).log(Level.SEVERE,
							"No se pudo cerrar el Resulset", ex);
				}
			}
		}
	}

	public void verCarta(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// TODO

	}

	public void eliminarCarta(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("Id");
		String idP = (String) req.getParameter("idP");
		ResultSet resultados = null;// Objeto para guardar los resultados
		try {
			Statement sentencia = conexion.createStatement();
			synchronized (sentencia) {
				// Cogemos todos los datos de los productos
				resultados = sentencia
						.executeQuery("select * from carta where idProducto='" + idP + "' and idCliente='" + id + "'");
			}
			if (!resultados.next()) {
				res.sendRedirect("Principal.jsp?mensaje=ERROR: No hay productos");
				return;
			} else {
				synchronized (sentencia) {
					// Cogemos todos los datos de los productos
					String query = "delete from carta where idCliente='" + id + "' and idProducto='" + idP + "'";
					sentencia.executeUpdate(query);
				}
				req.setAttribute("mensaje", "Eliminado del carrito perfectamente");
				req.getRequestDispatcher("WEB-INF/" + "Principal.jsp").forward(req, res);
			}

		} catch (SQLException e2) {
			res.sendRedirect("WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(CartaReyesController.class.getName()).log(Level.SEVERE,
							"No se pudo cerrar el Resulset", ex);
				}
			}
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
