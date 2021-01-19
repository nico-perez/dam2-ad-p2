package dev.el_nico.jardineria.dao.hql;

import dev.el_nico.jardineria.dao.DaoHolder;

public class ConexJardineriaHibernate extends DaoHolder<PedidosHqlDao,
                                                        ClientesHqlDao,
                                                        ProductosHqlDao> {

    public ConexJardineriaHibernate(PedidosHqlDao pedidosDao, ClientesHqlDao clientesDao, ProductosHqlDao productosDao) {
        super(pedidosDao, clientesDao, productosDao);
        // TODO Auto-generated constructor stub
    }

}
