package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.modelo.Reservas;

public class ReservaDAO {
	
	private Connection connection;
	
	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Reservas reserva) {
		try {
			String sql = "INSERT INTO reservas (fechaEntrada, fechaSalida, ValorTotal, FormaDePago) VALUES (?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setDate(1, reserva.getFechaEntrada());
				pstm.setDate(2, reserva.getFechaSalida());
				pstm.setString(3, reserva.getValorTotal());
				pstm.setString(4, reserva.getFormaDePago());

				pstm.executeUpdate();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Reservas> buscar() {
		List<Reservas> reservas = new ArrayList<Reservas>();
		try {
			String sql = "SELECT id, FechaEntrada, FechaSalida, ValorTotal, FormaDePago FROM reservas";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				{
					try (ResultSet rst = pstm.getResultSet()) {
						while (rst.next()) {
							Reservas reserva = new Reservas(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getString(4), rst.getString(5));

							reservas.add(reserva);
							
						}
					}
				}
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reservas> buscarId(String id) {
		List<Reservas> reservas = new ArrayList<Reservas>();
		try {

			String sql = "SELECT id, fechaEntrada, fechaSalida, ValorTotal, formaDePago FROM reservas WHERE id = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, id);
				pstm.execute();

				transformarResultSetEnReserva(reservas, pstm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Eliminar(Integer id) {
		try (PreparedStatement stm = connection.prepareStatement("DELETE FROM reservas WHERE id = ?")) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Actualizar(Date fechaEntrada, Date fechaSalida, String ValorTotal, String formaDePago, Integer id) {
		try (PreparedStatement stm = connection
				.prepareStatement("UPDATE reservas SET fechaEntrada = ?, fechaSalida = ?, ValorTotal = ?, formaDePago = ? WHERE id = ?")) {
			stm.setDate(1, fechaEntrada);
			stm.setDate(2, fechaSalida);
			stm.setString(3, ValorTotal);
			stm.setString(4, formaDePago);
			stm.setInt(5, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
						
	private void transformarResultSetEnReserva(List<Reservas> reservas, PreparedStatement pstm) throws  SQLException {
}
}
