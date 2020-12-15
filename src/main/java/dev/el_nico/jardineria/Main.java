package dev.el_nico.jardineria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import dev.el_nico.jardineria.dao.IDao;
import dev.el_nico.jardineria.dao.sql.ClientesSqlDao;
import dev.el_nico.jardineria.modelo.Cliente;
import dev.el_nico.jardineria.modelo.Pedido;

/**
 * Hello world!
 */
public class Main {
    private static IDao<Cliente> clientesDao;
    private static IDao<Pedido> pedidosDao;

    public static void main(String[] args) throws SQLException {

        try (Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/jardineria?serverTimezone=UTC", 
                                   "admin", 
                                   "admin")) { 
            
            clientesDao = new ClientesSqlDao(conexion);
            
            Optional<Cliente> clientes = clientesDao.uno(17);
            int i = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (clientesDao != null) {

            try {
                clientesDao.guardar(
                        new Cliente.Builder(39, 
                                            "el nico", 
                                            "3777438934", 
                                            "dfjjdf", 
                                            "cesar augusto 8", 
                                            "zaragoza"
                ).build());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
