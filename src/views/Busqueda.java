
package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jdbc.controller.InvitadosController;
import jdbc.controller.ReservasController;
import jdbc.modelo.Invitados;
import jdbc.modelo.Reservas;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private ReservasController reservaController;
	private InvitadosController huespedesController;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	String reserva;
	String huespedes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	private void abrirVentanaEdicionReserva() {
	    // Verifica si hay una fila seleccionada en la tabla de Reservas
	    int filaSeleccionada = tbReservas.getSelectedRow();
	    if (filaSeleccionada >= 0) {
	        // Crea una instancia de la ventana de edición (EditarReserva) y pásale el frame principal como argumento
	        EditarReserva editarReserva = new EditarReserva();

	        // Configura la ventana de edición con los valores actuales del registro seleccionado
	        String fechaEntrada = modelo.getValueAt(filaSeleccionada, 1).toString();
	        String fechaSalida = modelo.getValueAt(filaSeleccionada, 2).toString();
	        String valorTotal = modelo.getValueAt(filaSeleccionada, 3).toString();
	        String formaDePago = modelo.getValueAt(filaSeleccionada, 4).toString();
	        editarReserva.setValoresActuales(fechaEntrada, fechaSalida, valorTotal, formaDePago);

	        // Muestra la ventana de edición
	        editarReserva.setVisible(true);
	    } else {
	        // Si no hay una fila seleccionada, muestra un mensaje de error
	        JOptionPane.showMessageDialog(this, "Por favor, selecciona una fila en la tabla de Reservas.");
	    }
	}

	public Busqueda() {
		
		this.reservaController = new ReservasController();
		this.huespedesController = new InvitadosController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(tbReservas);
		
		
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblTitulo = new JLabel("SISTEMA DE BÚSQUEDA");
		lblTitulo.setBounds(331, 62, 400, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		contentPane.add(lblTitulo);
		
		JLabel lblSubTitulo = new JLabel("(POR ID)");
		lblSubTitulo.setBounds(421, 110, 400, 22); 
		lblSubTitulo.setForeground(new Color(12, 138, 199));
		lblSubTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		contentPane.add(lblSubTitulo);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(20, 169, 865, 328);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(panel);
		
		

		tbHuespedes = new JTable(modeloHuesped);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes, null);
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Numero de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Numero de Reserva");
		LlenarTablaHuespedes();

		
		
		
		tbReservas = new JTable();
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas, null);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		LlenarTablaReservas();
		
		JLabel logo = new JLabel("");
		logo.setBounds(56, 51, 104, 107);
		logo.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		contentPane.add(logo);
		
		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(539, 159, 193, 2);
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				if (txtBuscar.getText().equals("")) {					
					LlenarTablaHuespedes();
					LlenarTablaReservas();
					ActualizarReservas();
				} else {
				LlenarTablaReservasId();
				LlenarTablaHuespedesId();
			}
				}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int filaReservas = tbReservas.getSelectedRow();
		        int filaHuespedes = tbHuespedes.getSelectedRow();
		        
		        

		        if (filaReservas >= 0) {
		        	abrirVentanaEdicionReserva();
		            
		            limpiarTabla();
		            LlenarTablaReservas();
		            LlenarTablaHuespedes();
		            ActualizarReservas();
		        } else if (filaHuespedes >= 0) {
		        	abrirVentanaEdicionHuesped();
		           
		            limpiarTabla();
		            LlenarTablaHuespedes();
		            LlenarTablaReservas();
		            ActualizarHuesped();
		        }
		        	        
		    }
		});		
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		 
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();

				if (filaReservas >= 0) {

					reserva = tbReservas.getValueAt(filaReservas, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?"); 
					
					if(confirmar == JOptionPane.YES_OPTION){

						String valor = tbReservas.getValueAt(filaReservas, 0).toString();			
						reservaController.Eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Registro Eliminado");
						limpiarTabla();
						LlenarTablaReservas();
						LlenarTablaHuespedes();
					}
				}

				else if (filaHuespedes >= 0) {

					huespedes = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
					int confirmarh = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?"); 

					if(confirmarh == JOptionPane.YES_OPTION){

						String valor = tbHuespedes.getValueAt(filaHuespedes, 0).toString();			
						huespedesController.Eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Registro Eliminado");
						limpiarTabla();
						LlenarTablaHuespedes();
						LlenarTablaReservas();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Error fila no seleccionada, por favor realice una busqueda y seleccione una fila para eliminar");
				}							
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	private void abrirVentanaEdicionHuesped() {
	    // Verifica si hay una fila seleccionada en la tabla de Huespedes
	    int filaSeleccionada = tbHuespedes.getSelectedRow();
	    if (filaSeleccionada >= 0) {
	        // Crea una instancia de la ventana de edición (EditarHuesped) y pásale el frame principal como argumento
	        EditarHuesped editarHuesped = new EditarHuesped();

	        // Configura la ventana de edición con los valores actuales del registro seleccionado
	        String nombre = modeloHuesped.getValueAt(filaSeleccionada, 1).toString();
	        String apellido = modeloHuesped.getValueAt(filaSeleccionada, 2).toString();
	        String fechaNacimiento = modeloHuesped.getValueAt(filaSeleccionada, 3).toString();
	        String nacionalidad = modeloHuesped.getValueAt(filaSeleccionada, 4).toString();
	        String telefono = modeloHuesped.getValueAt(filaSeleccionada, 5).toString();

	        // Configura los valores en la ventana de edición
	        editarHuesped.setValoresActuales(nombre, apellido, fechaNacimiento, nacionalidad, telefono);

	        // Muestra la ventana de edición
	        editarHuesped.setVisible(true);
	    } else {
	        // Si no hay una fila seleccionada, muestra un mensaje de error
	        JOptionPane.showMessageDialog(this, "Por favor, selecciona una fila en la tabla de Huespedes.");
	    }
	}

	

	private List<Reservas> BuscarReservas() {
		return this.reservaController.buscar();
	}
	
	private List<Reservas> BuscarReservasId() {
		return this.reservaController.buscarId(txtBuscar.getText());
	} 
	private List<Invitados> BuscarHuespedes() {
		return this.huespedesController.listarInvitados();
	}
	
	private List<Invitados> BuscarHuespedesId() {
		return this.huespedesController.listarInvitadosId(txtBuscar.getText());
	}
	
	private void limpiarTabla() {
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}
	private void LlenarTablaReservas() {

	    // Llenar tabla
		List<Reservas> reserva = BuscarReservas();
		try {
			for (Reservas reservas : reserva) {
				modelo.addRow(new Object[] { reservas.getId(), reservas.getFechaEntrada(), reservas.getFechaSalida(), reservas.getValorTotal(), reservas.getFormaDePago() });
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void LlenarTablaReservasId() {

	    // Llenar tabla
		List<Reservas> reserva = BuscarReservasId();
		try {
			for (Reservas reservas : reserva) {
				modelo.addRow(new Object[] { reservas.getId(), reservas.getFechaEntrada(), reservas.getFechaSalida(), reservas.getValorTotal(), reservas.getFormaDePago() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void LlenarTablaHuespedes() {			       
	    //Llenar Tabla
		List<Invitados> huesped = BuscarHuespedes();
		try {
			for (Invitados invitados : huesped) {
				modeloHuesped.addRow(new Object[] { invitados.getIdInvitado(), invitados.getNombre(), invitados.getApellido(), invitados.getFecha_Nacimiento(), invitados.getNacionalidad(), invitados.getTelefono(), invitados.getIdReserva() });
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void LlenarTablaHuespedesId() {			       
	    //Llenar Tabla
		List<Invitados> huesped = BuscarHuespedesId();
		try {
			for (Invitados invitados : huesped) {
				modeloHuesped.addRow(new Object[] { invitados.getIdInvitado(), invitados.getNombre(), invitados.getApellido(), invitados.getFecha_Nacimiento(), invitados.getNacionalidad(), invitados.getTelefono(), invitados.getIdReserva() });
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void ActualizarReservas() {      
	    int filaSeleccionada = tbReservas.getSelectedRow();

	    if (filaSeleccionada >= 0) {
	        // Obtiene los valores actuales de la fila seleccionada
	        Date fechaEntrada = Date.valueOf(modelo.getValueAt(filaSeleccionada, 1).toString());
	        Date fechaSalida = Date.valueOf(modelo.getValueAt(filaSeleccionada, 2).toString());
	        String valorTotal = (String) modelo.getValueAt(filaSeleccionada, 3);
	        String formaDePago = (String) modelo.getValueAt(filaSeleccionada, 4);
	        Integer id = Integer.valueOf(modelo.getValueAt(filaSeleccionada, 0).toString());

	        // Llama al controlador para actualizar la reserva en la base de datos
	        if (reservaController.actualizar(fechaEntrada, fechaSalida, valorTotal, formaDePago, id)) {
	            modelo.setValueAt(fechaEntrada, filaSeleccionada, 1);
	            modelo.setValueAt(fechaSalida, filaSeleccionada, 2);
	            modelo.setValueAt(valorTotal, filaSeleccionada, 3);
	            modelo.setValueAt(formaDePago, filaSeleccionada, 4);
	            JOptionPane.showMessageDialog(this, "Registro modificado con éxito");
	        } else {
	            JOptionPane.showMessageDialog(this, "Error al actualizar la reserva");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Por favor, elige un registro");
	    }
	}




	
	private void ActualizarHuesped() {		
	    int filaSeleccionada = tbHuespedes.getSelectedRow();
	    if (filaSeleccionada >= 0) {
	        Optional<Object> optionalValue = Optional.ofNullable(modeloHuesped.getValueAt(filaSeleccionada, tbHuespedes.getSelectedColumn()));

	        if (optionalValue.isPresent()) {
	            String nombre = (String) modeloHuesped.getValueAt(filaSeleccionada, 1);
	            String apellido = (String) modeloHuesped.getValueAt(filaSeleccionada, 2);
	            Date fechaN = Date.valueOf(modeloHuesped.getValueAt(filaSeleccionada, 3).toString());
	            String nacionalidad = (String) modeloHuesped.getValueAt(filaSeleccionada, 4);
	            String telefono = (String) modeloHuesped.getValueAt(filaSeleccionada, 5);
	            Integer idReserva = Integer.valueOf(modeloHuesped.getValueAt(filaSeleccionada, 6).toString());
	            Integer id = Integer.valueOf(modeloHuesped.getValueAt(filaSeleccionada, 0).toString());
	            this.huespedesController.actualizar(nombre, apellido, fechaN, nacionalidad, telefono, idReserva, id);
	            JOptionPane.showMessageDialog(this, "Registro modificado con éxito");
	        } else {
	            JOptionPane.showMessageDialog(this, "Por favor, elige un registro");
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Por favor, selecciona una fila en la tabla de Huespedes.");
	    }
	}

	
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
