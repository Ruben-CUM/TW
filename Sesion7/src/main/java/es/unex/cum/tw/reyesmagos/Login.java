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
@WebServlet("/Login")
public class Login extends HttpServlet {
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
		String usuario = req.getParameter("user");
		String passRecibido = req.getParameter("password");
		ResultSet resultados = null;// Objeto para guardar los resultados
		try {
			if (usuario==null || passRecibido==null)
				res.sendRedirect("Error.html");
			String query = "SELECT * FROM usuarios where username=? and password=?";
			PreparedStatement sentencia = conexion.prepareStatement(query);
			sentencia.setString(1, usuario);
			sentencia.setString(2, passRecibido);
			resultados = sentencia.executeQuery();
			if (resultados.next() == false) {
				res.sendRedirect("Error.html");				
			} else {
				HttpSession session = req.getSession(true);
				String Id_Cliente = resultados.getString("id");
				String Nombre_Cliente = resultados.getString("nombre");
				String Username = resultados.getString("username");
				session.setAttribute("id", Id_Cliente);
				session.setAttribute("nombre", Nombre_Cliente);
				session.setAttribute("username", Username);
				res.sendRedirect("Introducir.html");
			}
		} catch (SQLException e2) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "No se pudo cerrar el Resulset", e2);

		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
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

	@Override
	public void destroy() {
		try {
			conexion.close();
		} catch (SQLException ex) {
			Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, "No se pudo cerrar el objeto Statement", ex);
		}
	}

}
