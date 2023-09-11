package jdbc.controller;

import jdbc.dao.UsuarioDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Usuario;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;

    public UsuarioController(ConnectionFactory connectionFactory) {
        this.usuarioDAO = new UsuarioDAO(connectionFactory.recuperarConexion());
    }

    public Usuario login(String usuario, String contrasena) {
        return usuarioDAO.login(usuario, contrasena);
    }
}
