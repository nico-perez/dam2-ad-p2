package dev.el_nico.jardineria;

import java.sql.SQLException;
import dev.el_nico.jardineria.dao.sql.ConexionJardineria;

/**
 * Hello world!
 */
public class Main {

    public static void main(String[] args) throws SQLException {

        try (ConexionJardineria c = new ConexionJardineria()) {

            String user, pass;
            do {
                user = System.console().readLine();
                pass = new String(System.console().readPassword());
            } while (!c.login(user, pass));
        
        }
    }
}
