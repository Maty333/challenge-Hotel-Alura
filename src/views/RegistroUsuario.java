package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroUsuario extends JFrame {

    private JPanel contentPane;
    private JTextField txtIdUsuario;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JPasswordField txtContrasena;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegistroUsuario frame = new RegistroUsuario();
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
    public RegistroUsuario() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra esta ventana sin salir de la aplicación
        setBounds(100, 100, 450, 400);
        setTitle("Registro de Usuario");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE); 
        
        Font labelFont = new Font("Roboto", Font.BOLD, 16);
        Font textFieldFont = new Font("Roboto", Font.PLAIN, 16);
        Color labelColor = new Color(12, 138, 199); 

        
        JLabel lblIdUsuario = new JLabel("Usuario:");
        lblIdUsuario.setFont(labelFont);
        lblIdUsuario.setForeground(labelColor);
        lblIdUsuario.setBounds(100, 30, 120, 20);
        contentPane.add(lblIdUsuario);

        txtIdUsuario = new JTextField();
        txtIdUsuario.setBounds(180, 30, 200, 20);
        txtIdUsuario.setFont(textFieldFont);
        contentPane.add(txtIdUsuario);
        txtIdUsuario.setColumns(10);

       
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelFont);
        lblNombre.setForeground(labelColor);
        lblNombre.setBounds(100, 58, 120, 20);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 60, 200, 20);
        txtNombre.setFont(textFieldFont);
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

       
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(labelFont);
        lblApellido.setForeground(labelColor);
        lblApellido.setBounds(100, 88, 120, 20);
        contentPane.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(180, 90, 200, 20);
        txtApellido.setFont(textFieldFont);
        contentPane.add(txtApellido);
        txtApellido.setColumns(10);

        
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(labelFont);
        lblTelefono.setForeground(labelColor);
        lblTelefono.setBounds(98, 120, 120, 20);
        contentPane.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(180, 120, 200, 20);
        txtTelefono.setFont(textFieldFont);
        contentPane.add(txtTelefono);
        txtTelefono.setColumns(10);

        
        JLabel lblEmail = new JLabel("Correo Electrónico:");
        lblEmail.setFont(labelFont);
        lblEmail.setForeground(labelColor);
        lblEmail.setBounds(24, 150, 160, 20);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(180, 150, 200, 20);
        txtEmail.setFont(textFieldFont);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(labelFont);
        lblContrasena.setForeground(labelColor);
        lblContrasena.setBounds(80, 180, 120, 20);
        contentPane.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(180, 180, 200, 20);
        txtContrasena.setFont(textFieldFont);
        contentPane.add(txtContrasena);

        // Botón de registro
        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(180, 220, 200, 30);
        btnRegistrar.setBackground(new Color(12, 138, 199)); // Fondo azul
        btnRegistrar.setForeground(Color.WHITE); // Texto blanco
        btnRegistrar.setFont(labelFont);
        contentPane.add(btnRegistrar);
        

     
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtIdUsuario.getText();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String telefono = txtTelefono.getText();
                String email = txtEmail.getText();
                String contrasena = new String(txtContrasena.getPassword());

                
                if (usuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Por favor, complete todos los campos.");
                } else {
                    
                    String url = "jdbc:mysql://localhost:3306/hotel_alura";
                    String user = "root";
                    String password = "matyLucy30!";

                    try {
                        Connection conn = DriverManager.getConnection(url, user, password);

                        
                        String sql = "INSERT INTO usuario (usuario, nombre, apellido, telefono, email, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);

                       
                        stmt.setString(1, usuario);
                        stmt.setString(2, nombre);
                        stmt.setString(3, apellido);
                        stmt.setString(4, telefono);
                        stmt.setString(5, email);
                        stmt.setString(6, contrasena);

                        // Ejecuta la inserción
                        int rowsAffected = stmt.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(contentPane, "Registro exitoso.");
                            Login loginFrame = new Login();
                            loginFrame.setVisible(true);
                            dispose(); 
                        } else {
                            JOptionPane.showMessageDialog(contentPane, "Error al registrar usuario.");
                        }

                        // Cierra la conexión y la declaración
                        stmt.close();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(contentPane, "Error al conectar a la base de datos.");
                    }
                }
            }
        });

        setLocationRelativeTo(null);

    }
}


