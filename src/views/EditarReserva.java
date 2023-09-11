package views;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class EditarReserva extends JFrame {
    private JDateChooser dateChooserFechaEntrada;
    private JDateChooser dateChooserFechaSalida;
    private JTextField txtValorTotal;
    private JTextField txtFormaDePago;
    private int Id;
    
    
    public void editarReservaId(int id) {
    	this.Id = id;
    	
    }

    public EditarReserva() {
        setTitle("Editar Reserva");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Initialize the components
        dateChooserFechaEntrada = new JDateChooser();
        dateChooserFechaSalida = new JDateChooser();
        txtValorTotal = new JTextField(10);
        txtValorTotal.setEditable(false);
        txtFormaDePago = new JTextField(10);
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        // Add PropertyChangeListeners to date chooser components
        dateChooserFechaEntrada.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                calcularValor();
            }
        });

        dateChooserFechaSalida.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                calcularValor();
            }
        });

        // Action listener for the "Cancelar" button
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the edit window
            }
        });

        // Action listener for the "Guardar" button
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarReserva();
            }
        });

        // Create a panel to organize components
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Fecha de Entrada:"));
        panel.add(dateChooserFechaEntrada);
        panel.add(new JLabel("Fecha de Salida:"));
        panel.add(dateChooserFechaSalida);
        panel.add(new JLabel("Valor Total:"));
        panel.add(txtValorTotal);
        panel.add(new JLabel("Forma de Pago:"));
        panel.add(txtFormaDePago);
        panel.add(btnGuardar);
        panel.add(btnCancelar);
        add(panel);
        setLocationRelativeTo(null);
    }

    private void calcularValor() {
        // Get the selected entry and exit dates
    	java.util.Date fechaEntrada = dateChooserFechaEntrada.getDate();
        java.util.Date fechaSalida = dateChooserFechaSalida.getDate();

        if (fechaEntrada != null && fechaSalida != null) {
            
            int diaria = 500;
            long diff = fechaSalida.getTime() - fechaEntrada.getTime();
            int dias = (int) (diff / (24 * 60 * 60 * 1000));
            int valorTotalCalculado = dias * diaria;

            // Display the calculated total value in the txtValorTotal field
            txtValorTotal.setText(String.valueOf(valorTotalCalculado));
        } else {
        	txtValorTotal.setText("");
        }
    }

    // Method to modify the reservation
    private void modificarReserva() {
        // Retrieve the values from the text fields
        Date fechaEntrada = new Date(dateChooserFechaEntrada.getDate().getTime());
        Date fechaSalida = new Date(dateChooserFechaSalida.getDate().getTime());
        String valorTotal = txtValorTotal.getText();
        String formaDePago = txtFormaDePago.getText();

        if (fechaEntrada == null || fechaSalida == null || valorTotal.isEmpty() || formaDePago.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        } else {
            // Database connection settings
            String url = "jdbc:mysql://localhost:3306/hotel_alura";
            String user = "root";
            String password = "matyLucy30!";

            try {
                Connection conn = DriverManager.getConnection(url, user, password);

                String sql = "UPDATE reservas SET FechaEntrada=?, FechaSalida=?, ValorTotal=?, FormaDePago=? WHERE Id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setDate(1, fechaEntrada);
                stmt.setDate(2, fechaSalida);
                stmt.setString(3, valorTotal);
                stmt.setString(4, formaDePago);
                stmt.setInt(5, Id); // Corregido aquí

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Registro modificado.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al modificar registro.");
                }

                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos.");
            }
        }
    }

    public void setValoresActuales(String fechaEntrada, String fechaSalida, String valorTotal, String FormaDePago) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fechaEntradaUtil = dateFormat.parse(fechaEntrada);
            java.util.Date fechaSalidaUtil = dateFormat.parse(fechaSalida);

            // Set the values in the date chooser components and text fields
            dateChooserFechaEntrada.setDate(fechaEntradaUtil);
            dateChooserFechaSalida.setDate(fechaSalidaUtil);
            txtValorTotal.setText(valorTotal);
            txtFormaDePago.setText(FormaDePago);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al parsear las fechas.");
        }
    }
}
