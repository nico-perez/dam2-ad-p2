package dev.el_nico.jardineria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import dev.el_nico.jardineria.dao.ClientesGsonDao;
import dev.el_nico.jardineria.dao.ClientesSQLDao;
import dev.el_nico.jardineria.dao.IDataAccessObject;
import dev.el_nico.jardineria.dao.PedidosGsonDao;
import dev.el_nico.jardineria.excepciones.ExcepcionClienteDuplicado;
import dev.el_nico.jardineria.excepciones.ExcepcionCodigoYaExistente;
import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.excepciones.ExcepcionFormatoIncorrecto;
import dev.el_nico.jardineria.modelo.Cliente;
import dev.el_nico.jardineria.modelo.Pedido;

/**
 * Hello world!
 */
public class Main {
    private static IDataAccessObject<Cliente> clientesDao;
    private static IDataAccessObject<Pedido> pedidosDao;

    public static void main(String[] args) throws SQLException {

        try {
            Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/jardineria?serverTimezone=UTC", "admin", "admin");
            clientesDao = new ClientesSQLDao(conexion);
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
