package dev.el_nico.jardineria.dao.sql;

import org.junit.jupiter.api.Test;

public class ProductosSqlDaoTest extends SqlTest {

    @Test
    public void testModificar() {
        productos.modificar(productos.uno("11679").get(), new Object[] {null, "nombre nuevo", null, "dimnes nuevas","prov. nuevo", "desc. nueva", 69, 420.69, 666.0});
    }
}
