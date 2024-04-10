package es.unex.cum.tw.reyesmagos.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unex.cum.tw.reyesmagos.controller.ProductosController;
import es.unex.cum.tw.reyesmagos.model.Carta;
import es.unex.cum.tw.reyesmagos.model.Producto;
import es.unex.cum.tw.reyesmagos.model.Usuario;
import util.ConexionUtil;

public class CartaServiceBD implements CartaService {

	public boolean anadirCarta(String idP, String id) throws IOException, SQLException {
		ResultSet resultados = null;
		Carta aux;
		String query = "select * from productos where idProductos=?";
		PreparedStatement sent = ConexionUtil.openConnection().prepareStatement(query);
		sent.setString(1, idP);
		resultados = sent.executeQuery();
		if (resultados.next()) {
			return false;
		} else {
			aux = new Carta(Integer.parseInt(resultados.getString(1)), resultados.getString(2),
					resultados.getString(3),Integer.parseInt( resultados.getString(4)));
			try {

				query = "INSERT INTO carta (nombre,nombreProd,cantidad) "
						+ "VALUES (?,?,?)";
				PreparedStatement sentencia = (PreparedStatement) ConexionUtil.openConnection().prepareStatement(query);
				synchronized (sentencia) {
					sentencia.setString(1, aux.getNombre());
					sentencia.setString(2, aux.getNombreProd());
					sentencia.setInt(3, aux.getCantidad());
					sentencia.executeUpdate();
				}

			} catch (Exception e) {
				e.printStackTrace();
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
		return true;
	}

	public boolean eliminarCarta(String idP, String id) throws IOException, SQLException {
		// TODO
		return false;
	}

	public List<Carta> verCarta(String idP, String id) throws IOException, SQLException {

		Usuario user = null;
		ResultSet resultados = null;
		List<Carta> l = new ArrayList<Carta>();
		try {
			String query = "SELECT * FROM carta";
			PreparedStatement sentencia = (PreparedStatement) ConexionUtil.openConnection().prepareStatement(query);
			resultados = sentencia.executeQuery();

			while (resultados.next()) {
				Carta carta = new Carta(Integer.parseInt(resultados.getString("idProducto")),
						resultados.getString("nombre"), resultados.getString("nombreProd"),
						Integer.parseInt(resultados.getString("cantidad")));
				l.add(carta);

			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return l;
	}

}
