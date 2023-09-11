package jdbc.controller;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import jdbc.dao.InvitadosDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Invitados;


public class InvitadosController {
	 private InvitadosDAO invitadosDAO;
	 
	 public InvitadosController() {
			Connection connection = new ConnectionFactory().recuperarConexion();
			this.invitadosDAO = new InvitadosDAO(connection);
		}
	 
		public void guardar(Invitados invitados) {
			this.invitadosDAO.guardar(invitados);
		}
		public List<Invitados> listarInvitados() {
			return this.invitadosDAO.listarInvitados();
		}
		
		public List<Invitados> listarInvitadosId(String id) {
			return this.invitadosDAO.buscarId(id);
		}
		
		public void actualizar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, Integer idReserva, Integer id) {
			this.invitadosDAO.Actualizar(nombre, apellido, fechaN, nacionalidad, telefono, idReserva, id);
		}
		
		public void Eliminar(Integer id) {
			this.invitadosDAO.Eliminar(id);
		}
}
