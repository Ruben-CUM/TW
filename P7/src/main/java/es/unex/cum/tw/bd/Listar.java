package es.unex.cum.tw.bd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet("/ListarBD")
public class Listar extends HttpServlet {

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
		PrintWriter out=res.getWriter();
		try {
			String query = "SELECT * FROM usuarios";
			sentencia = conexion.prepareStatement(query);
			resultados = sentencia.executeQuery();
			while (resultados.next()) {
				out.println(resultados.getString(1) + " " + resultados.getString(2) + " "
						+ resultados.getString(3));
			}

		} catch (SQLException e2) {
			Logger.getLogger(Listar.class.getName()).log(Level.SEVERE, "No se pudo cerrar el Resulset", e2);
		} finally {
			if (sentencia != null) { // Se cierra resultSet
				try {
					sentencia.close();
				} catch (SQLException ex) {
					Logger.getLogger(Listar.class.getName()).log(Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}

	}

	@Override
	public void destroy() {
		try {
			conexion.close();
		} catch (SQLException ex) {
			Logger.getLogger(Listar.class.getName()).log(Level.SEVERE, "No se pudo cerrar el objeto Statement", ex);
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
