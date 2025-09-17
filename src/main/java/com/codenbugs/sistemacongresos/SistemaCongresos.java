package com.codenbugs.sistemacongresos;

import com.codenbugs.sistemacongresos.dao.UsuarioDAO;
import com.codenbugs.sistemacongresos.modelos.Usuario;
import com.codenbugs.sistemacongresos.util.DBConnection;

import java.sql.SQLException;
import java.util.List;

public class SistemaCongresos {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        DBConnection connection = new DBConnection();
        connection.connect();

        // Nueva prueba: listar usuarios con DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            System.out.println("Usuarios en la base (" + usuarios.size() + "):");
            for (Usuario u : usuarios) {
                System.out.println(" - " + u); // toString() sin password/foto
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios");
            e.printStackTrace();
        }
    }
}
