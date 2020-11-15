package dev.el_nico.jardineria.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import dev.el_nico.jardineria.excepciones.ExcepcionClienteNoEncontrado;
import dev.el_nico.jardineria.modelo.Pedido;

public class PedidosDaoTest {

    @Test
    public void LanzaExcepcionSiElClienteNoExiste() {
        PedidosDao dao = new PedidosDao(new ClientesDao());
        try { // ClientesDao tiene clientes con código del 1 al 4
            dao.guardar(new Pedido.Builder(5, 3, "bien", 5).build());
            fail();
        } catch (ExcepcionClienteNoEncontrado e) {
            // oke
        } catch (Exception e) {
            fail();
        }
        try { // ClientesDao tiene clientes con código del 1 al 4
            dao.guardar(new Pedido.Builder(6, 3, "bien", 6).build());
            fail();
        } catch (ExcepcionClienteNoEncontrado e) {
            // oke
        } catch (Exception e) {
            fail();
        }
        try { // ClientesDao tiene clientes con código del 1 al 4
            dao.guardar(new Pedido.Builder(7, 3, "bien", 7).build());
            fail();
        } catch (ExcepcionClienteNoEncontrado e) {
            // oke
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }
}
