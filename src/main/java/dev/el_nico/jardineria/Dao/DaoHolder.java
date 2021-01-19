package dev.el_nico.jardineria.dao;

import dev.el_nico.jardineria.modelo.Cliente;
import dev.el_nico.jardineria.modelo.Pedido;
import dev.el_nico.jardineria.modelo.Producto;

public class DaoHolder<tipoDaoPedidos extends IDao<Pedido>, 
                       tipoDaoClientes extends IDao<Cliente>,
                       tipoDaoProductos extends IDao<Producto>> {
    
    protected tipoDaoPedidos pedidosDao;
    protected tipoDaoClientes clientesDao;
    protected tipoDaoProductos productosDao;

    public DaoHolder(tipoDaoPedidos pedidosDao, tipoDaoClientes clientesDao, tipoDaoProductos productosDao) {
        this.pedidosDao = pedidosDao;
        this.clientesDao = clientesDao;
        this.productosDao = productosDao;
    }

    public tipoDaoPedidos pedidos() {
        return pedidosDao;
    }

    public tipoDaoClientes clientes() {
        return clientesDao;
    }

    public tipoDaoProductos productos() {
        return productosDao;
    }
}
