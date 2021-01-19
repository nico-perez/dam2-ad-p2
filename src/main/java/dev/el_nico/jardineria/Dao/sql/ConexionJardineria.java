package dev.el_nico.jardineria.dao.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dev.el_nico.jardineria.dao.DaoHolder;

public class ConexionJardineria extends DaoHolder<PedidosSqlDao, ClientesSqlDao, ProductosSqlDao> implements AutoCloseable {

    private Connection conn;

    public ConexionJardineria() {
        super(null, null, null);
    }

    public boolean login(String user, String pass) {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jardineria?serverTimezone=UTC", user, pass);
            
            pedidosDao = new PedidosSqlDao(conn);
            clientesDao = new ClientesSqlDao(conn);
            productosDao = new ProductosSqlDao(conn);

            return true;
        } catch (SQLException e) {
            System.out.println("Error al loginear.");
            return false;
        }
    }

    @Override
    public void close() throws SQLException {
        if (conn != null) {
            if (!conn.getAutoCommit()) conn.commit();
            conn.close();
        }
    }
}
