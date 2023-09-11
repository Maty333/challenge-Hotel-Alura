package jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reservas;

public class ReservasController {
 private ReservaDAO reservaDAO;
 
 public ReservasController() {
		Connection connection = new ConnectionFactory().recuperarConexion();
		this.reservaDAO = new ReservaDAO(connection);
	}
 
	public void guardar(Reservas reserva) {
		this.reservaDAO.guardar(reserva);
	}
		
	public List<Reservas> buscar() {
		return this.reservaDAO.buscar();
	}
	
	public List<Reservas> buscarId(String id) {
		return this.reservaDAO.buscarId(id);
	}
	
	public boolean actualizar(Date fechaEntrada, Date fechaSalida, String valorTotal, String formaDePago, Integer id) {
	    try {
	       
	        this.reservaDAO.Actualizar(fechaEntrada, fechaSalida, valorTotal, formaDePago, id);
	        
	        return true;
	    } catch (Exception e) {
	        
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public void Eliminar(Integer id) {
		this.reservaDAO.Eliminar(id);
	}
}
