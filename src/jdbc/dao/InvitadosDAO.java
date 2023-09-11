package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Invitados;
import jdbc.modelo.Reservas;


public class InvitadosDAO {
private Connection connection;
	
	public InvitadosDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Invitados invitado) {
		try {
			String sql = "INSERT INTO invitados (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva) VALUES (?, ?, ?, ?,?,?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, invitado.getNombre());
				pstm.setString(2, invitado.getApellido());
				pstm.setDate(3, invitado.getFecha_Nacimiento());
				pstm.setString(4, invitado.getNacionalidad());
				pstm.setString(5, invitado.getTelefono());
				pstm.setInt(6, invitado.getIdReserva());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						invitado.setIdInvitado(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Invitados> listarInvitados() {
		List<Invitados> invitados = new ArrayList<Invitados>();
		try {
			String sql = "SELECT idInvitado, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva FROM invitados";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				transformarResultSetEnInvitados(invitados, pstm);
			}
			return invitados;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Invitados> buscarId(String id) {
		List<Invitados> invitados = new ArrayList<Invitados>();
		try {

			String sql = "SELECT idInvitado, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva FROM invitados WHERE idReserva = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, id);
				pstm.execute();

				transformarResultSetEnInvitados(invitados, pstm);
			}
			return invitados;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Actualizar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, Integer idReserva, Integer id) {
		try (PreparedStatement stm = connection
				.prepareStatement("UPDATE invitados SET nombre = ?, apellido = ?, fecha_nacimiento = ?, nacionalidad = ?, telefono = ?, idReserva = ? WHERE idInvitado = ?")) {
			stm.setString(1, nombre);
			stm.setString(2, apellido);
			stm.setDate(3, fechaN);
			stm.setString(4, nacionalidad);
			stm.setString(5, telefono);
			stm.setInt(6, idReserva);
			stm.setInt(7, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void Eliminar(Integer id) {
		try (PreparedStatement stm = connection.prepareStatement("DELETE FROM invitados WHERE idInvitado = ?")) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void transformarResultSetEnInvitados(List<Invitados> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Invitados invitados = new Invitados(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDate(4), rst.getString(5), rst.getString(6), rst.getInt(7));
				reservas.add(invitados);
			}
		}				
	}
	
	
		
}

