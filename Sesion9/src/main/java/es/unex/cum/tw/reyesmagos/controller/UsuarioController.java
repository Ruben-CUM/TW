package es.unex.cum.tw.reyesmagos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {

	private Connection conexion = null;
	private DataSource servicioConexiones = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			// Referencia al servicio de conexiones
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			servicioConexiones = (DataSource) envContext.lookup("jdbc/testdb");
			conexion = servicioConexiones.getConnection();
		} catch (Exception e) {
			throw new ServletException(
					"Imposible recuperar java:comp/env/jdbc/testdb", e);
		}
	}

	public void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String accion = req.getParameter("action");
		if (accion.equals("UsuarioLogin")) {
			doLogin(req, res);
		} else if (accion.equals("UsuarioAlta")) {
			darAlta(req, res);
		} else if (accion.equals("InfoUsuario")) {
			//TODO infoUsuario
		}
		else {
			res.sendRedirect("Error.jsp?error=No hay acción");
		}

	}

	public void infoUsuario(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		//TODO
	}
	
	public void doLogin(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String usuario = req.getParameter("user");
		String passRecibido = req.getParameter("password");
		String passBD = null;
		ResultSet resultados = null;// Objeto para guardar los resultados
		try {
			if (usuario==null || passRecibido==null)
				res.sendRedirect("Error.jsp?error=Introduce usuario o Contraseña");
			
			String query = "SELECT * FROM usuarios where username=? and password=?";
			PreparedStatement sentencia = conexion.prepareStatement(query);
			sentencia.setString(1, usuario);
			sentencia.setString(2, passRecibido);
			resultados = sentencia.executeQuery();
			if (resultados.next() == false) {
				res.sendRedirect("Error.jsp?error=Usuario o Contraseña erronea");
			} else {
				HttpSession session = req.getSession(true);
				String Id_Cliente = resultados.getString("id");
				String Nombre_Cliente = resultados.getString("nombre");
				String Username = resultados.getString("username");
				session.setAttribute("id", Id_Cliente);
				session.setAttribute("nombre", Nombre_Cliente);
				session.setAttribute("username", Username);
				req.setAttribute("mensaje", "Autenticacion correcta");
				req.getRequestDispatcher("WEB-INF/Principal.jsp").forward(req, res);
			}

		} catch (SQLException e2) {
			// Error SQL: login/passwd mal
			res.sendRedirect("Error.jsp?error=ERROR:Fallo en SQL");

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}
	}

	public void darAlta(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String usuario = null;
		usuario = req.getParameter("username");
		ResultSet resultados = null;
		PreparedStatement sentencia = null;
	
		try {
			String query = "SELECT * FROM usuarios where username=?";
			sentencia = conexion.prepareStatement(query);
			sentencia.setString(1, usuario);
			resultados = sentencia.executeQuery();
			if (resultados.next() == true) {
				res.sendRedirect("Error.jsp?error=El usuario ya existe");
			} else {
				resultados.close();
				sentencia.close();
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
				req.setAttribute("mensaje", "Dado de alta correctamente");
				req.getRequestDispatcher("Login.jsp").forward(req, res);
			}
		} catch (SQLException e2) {
			// Error SQL: login/passwd mal
			res.sendRedirect("Error.jsp?error=ERROR:Fallo en SQL");

		} finally {
			// Se cierra resultSet
			if (sentencia != null) {
				try {
					sentencia.close();
				} catch (SQLException ex) {
					Logger.getLogger(UsuarioController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		RequestDispatcher view = req.getRequestDispatcher("WEB-INF/Login.jsp");
		// RequestDispatcher view = request.getRequestDispatcher("pages/login.html");
		view.forward(req, res);
	}

	@Override
	public void destroy() {
			try {
				conexion.close();
			} catch (SQLException ex) {
				Logger.getLogger(UsuarioController.class.getName()).log(
						Level.SEVERE, "No se pudo cerrar el objeto Conexion",
						ex);
		}
	}
}
