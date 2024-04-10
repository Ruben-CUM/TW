package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import es.unex.cum.tw.reyesmagos.model.Producto;
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
@WebServlet(urlPatterns="/Restringido/ProductosController")
public class ProductosController extends HttpServlet {

	private Connection conexion = null;
	private DataSource servicioConexiones = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			servicioConexiones = (DataSource) envContext.lookup("jdbc/testdb");
			conexion = servicioConexiones.getConnection();

		} catch (Exception e) {
			throw new ServletException("Imposible recuperar java:comp/env/jdbc/testdb", e);
		}
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		//No hace falta por el filter
		/*
		HttpSession session = req.getSession();

		String id = (String) session.getAttribute("id");
		if (id != null) {*/
			String accion = req.getParameter("action");
			if (accion.equals("VerProductos")) {
				verTodos(req, res);
			} else if (accion.equals("VerProducto")) {
				verProducto(req, res);
			} else {
				res.sendRedirect("../WEB-INF/Principal.jsp?mensaje=No hay acción");
			}

	/*	} else {
			res.sendRedirect("Error.jsp?error=Problemas con la sesión");
		}*/
	}

	public void verTodos(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ResultSet resultados = null;// Objeto para guardar los resultados

		try {
			Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			synchronized (sentencia) {
				// Cogemos todos los datos de los productos
				resultados = sentencia.executeQuery("select * from productos");
			}
			if (!resultados.next()) {
				res.sendRedirect("WEB-INF/Principal.jsp?mensaje=ERROR: No hay productos");
			} else {
				resultados.beforeFirst();
				ArrayList l = new ArrayList();
				while (resultados.next()) {
					l.add(new Producto(Integer.parseInt(resultados.getString(1)), resultados.getString(2),
							resultados.getString(3), resultados.getString(4)));
				}
				req.setAttribute("lista", l);
				req.getRequestDispatcher("../WEB-INF/VerProductos.jsp").forward(req, res);
			}

		} catch (SQLException e2) {
			res.sendRedirect("../WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE,
							"No se pudo cerrar el Resulset", ex);
				}
			}
		}
	}

	public void verProducto(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ResultSet resultados = null;// Objeto para guardar los resultados
		String idP = (String) req.getParameter("idP");
		try {
			String query = "select * from productos where idProductos=?";
			PreparedStatement sentencia = conexion.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia.setString(1, idP);
			resultados = sentencia.executeQuery();
			if (!resultados.next()) {
				res.sendRedirect("Principal.jsp?mensaje=ERROR: No hay productos");
			} else {
				Producto p = new Producto(Integer.parseInt(resultados.getString(1)), resultados.getString(2),
						resultados.getString(3), resultados.getString(4));
				req.setAttribute("producto", p);
				req.getRequestDispatcher("../WEB-INF/VerProducto.jsp").forward(req, res);
			}

		} catch (SQLException e2) {
			res.sendRedirect("../WEB-INF/Principal.jsp?mensaje=Error en la consulta. Hable con administrador");

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE,
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

	@Override
	public void destroy() {
		try {
			conexion.close();
		} catch (SQLException ex) {
			Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE,
					"No se pudo cerrar el objeto Conexion", ex);
		}
	}

}
