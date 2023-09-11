package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditarHuesped extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtFechaNacimiento;
    private JTextField txtNacionalidad;
    private JTextField txtTelefono;
    private int huespedId;

    public EditarHuesped() {
        setTitle("Editar Huésped");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Initialize the components
        txtNombre = new JTextField(10);
        txtApellido = new JTextField(10);
        txtFechaNacimiento = new JTextField(10);
        txtNacionalidad = new JTextField(10);
        txtTelefono = new JTextField(10);
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

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
                modificarHuesped();
            }
        });

        // Create a panel to organize components
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Fecha de Nacimiento:"));
        panel.add(txtFechaNacimiento);
        panel.add(new JLabel("Nacionalidad:"));
        panel.add(txtNacionalidad);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(btnGuardar);
        panel.add(btnCancelar);
        add(panel);
        setLocationRelativeTo(null);
    }

    public void editarHuespedId(int id) {
        this.huespedId = id;
        
        // Recuperar los datos actuales del huésped y completar los campos correspondientes
        String url = "jdbc:mysql://localhost:3306/hotel_alura";
        String usuario = "root";
        String contraseña = "matyLucy30!";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, contraseña);

            String sql = "SELECT Nombre, Apellido, Fecha_Nacimiento, Nacionalidad, Telefono FROM invitados WHERE IdInvitado=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, huespedId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Obtener los valores de la consulta y establecerlos en los campos de texto
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String fechaNacimiento = rs.getString("Fecha_Nacimiento");
                String nacionalidad = rs.getString("Nacionalidad");
                String telefono = rs.getString("Telefono");

                setValoresActuales(nombre, apellido, fechaNacimiento, nacionalidad, telefono);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos.");
        }
    }
    // Método para modificar el huésped en la base de datos
    private void modificarHuesped() {
        // Obtener los valores de los campos de texto
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String fechaNacimiento = txtFechaNacimiento.getText();
        String nacionalidad = txtNacionalidad.getText();
        String telefono = txtTelefono.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || fechaNacimiento.isEmpty() || nacionalidad.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        } else {
            // Establecer la conexión a la base de datos y ejecutar la actualización
            String url = "jdbc:mysql://localhost:3306/hotel_alura";
            String usuario = "root";
            String contraseña = "matyLucy30!";

            try {
                Connection conn = DriverManager.getConnection(url, usuario, contraseña);

                String sql = "UPDATE invitados SET Nombre=?, Apellido=?, Fecha_Nacimiento=?, Nacionalidad=?, Telefono=? WHERE IdInvitado=?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, nombre);
                stmt.setString(2, apellido);
                stmt.setString(3, fechaNacimiento);
                stmt.setString(4, nacionalidad);
                stmt.setString(5, telefono);
                stmt.setInt(6, huespedId);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Huésped modificado con éxito");
                    dispose(); // Cerrar la ventana de edición
                } else {
                    JOptionPane.showMessageDialog(this, "Error al modificar el huésped");
                }

                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos.");
            }
        }
    }

    // Método para establecer los valores actuales del huésped en los campos de texto
    public void setValoresActuales(String nombre, String apellido, String fechaNacimiento, String fechaNacimiento2, String telefono) {
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtFechaNacimiento.setText(fechaNacimiento);
        txtNacionalidad.setText(fechaNacimiento2);
        txtTelefono.setText(telefono);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditarHuesped frame = new EditarHuesped();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
