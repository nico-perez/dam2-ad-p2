package dev.el_nico.jardineria.dao.hql;

import org.hibernate.Session;

public class ConexJardineriaHibernate {

    private ClientesHqlDao clientesDao;
    private PedidosHqlDao pedidosDao;
    private ProductosHqlDao productosDao;

	private Session s;

    public ConexJardineriaHibernate(Session s) {
        clientesDao = new ClientesHqlDao(s);
        pedidosDao = new PedidosHqlDao(s);
		productosDao = new ProductosHqlDao(s);
		
		this.s = s;
    }

	public ClientesHqlDao clientes() {
		return clientesDao;
	}

	public PedidosHqlDao pedidos() {
		return pedidosDao;
	}

	public ProductosHqlDao productos() {
		return productosDao;
	}

	public void close() {
		s.close();
	}
}
