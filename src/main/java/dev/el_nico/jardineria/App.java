package dev.el_nico.jardineria;

import dev.el_nico.jardineria.Modelo.Cliente;
import dev.el_nico.jardineria.Modelo.Pedido;
import dev.el_nico.jardineria.Dao.ClientesGsonDao;
import dev.el_nico.jardineria.Dao.IDataAccessObject;
import dev.el_nico.jardineria.Dao.PedidosGsonDao;
import dev.el_nico.jardineria.Excepciones.ExcepcionClienteDuplicado;
import dev.el_nico.jardineria.Excepciones.ExcepcionCodigoYaExistente;
import dev.el_nico.jardineria.Excepciones.ExcepcionDatoNoValido;

/**
 * Hello world!
 */
public class App 
{
    // DAO<Cliente>
    private static IDataAccessObject<Cliente> clientesDao;
    private static IDataAccessObject<Pedido> pedidosDao;

    public static void main(String[] args) {

        clientesDao = new ClientesGsonDao("clientes.json");
        pedidosDao = new PedidosGsonDao("pedidos.json", clientesDao);

        try {
            clientesDao.guardar(new Cliente.Builder(6, "aaa", "aaa", "aaa", "aaa", "aaa").build());
            clientesDao.guardar(new Cliente.Builder(7, "el nico", "1", "2", "Calle ", "zarago<a").con_region("pais aragones").build());
        } catch (ExcepcionClienteDuplicado e) {
            System.err.println("cliente duplicado");
        } catch (ExcepcionCodigoYaExistente e) {
            System.err.println("codigo ya existente");
        } catch (ExcepcionDatoNoValido e) {
            System.err.println("dato no valido");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            System.err.println("?????");
        }

        try {
            pedidosDao.guardar(new Pedido.Builder(69, 530, "en ello", 5).con_comentarios("aaa").build());
            pedidosDao.guardar(new Pedido.Builder(40, 4, "bien!", 7).build());
        } catch (ExcepcionDatoNoValido e) {
            System.err.println("codigo de cliente no existe");
        } catch (ExcepcionCodigoYaExistente e) {
            System.err.println("codigo de pedido ya existe");
        } catch (Exception e) {
            System.err.println("???????");
        }
    }
}
