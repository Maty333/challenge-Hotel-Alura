package jdbc.modelo;

import java.sql.Date;

public class Reservas {
	
	private Integer id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private String valorTotal;
	private String formaDePago;
	
	
	public Reservas(Integer id, Date fechaEntrada, Date fechaSalida, String valorTotal, String formaDePago) {

		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valorTotal = valorTotal;
		this.formaDePago = formaDePago;
	}


	public Reservas(Date fechaEntrada, Date fechaSalida, String valorTotal, String formaDePago) {
		
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valorTotal = valorTotal;
		this.formaDePago = formaDePago;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getFechaEntrada() {
		return fechaEntrada;
	}


	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}


	public Date getFechaSalida() {
		return fechaSalida;
	}


	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}


	public String getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}


	public String getFormaDePago() {
		return formaDePago;
	}


	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return String.format("La reserva generada fue: %d, %s, %s, %s, %s", this.id, this.fechaE, this.fechaS, this.valor, this.formaPago);
//	}
//	
	
	
}

