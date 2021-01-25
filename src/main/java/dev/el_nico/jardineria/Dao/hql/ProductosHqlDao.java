package dev.el_nico.jardineria.dao.hql;

import java.util.List;
import java.util.Optional;

import dev.el_nico.jardineria.dao.IDao;
import dev.el_nico.jardineria.modelo.Producto;

public class ProductosHqlDao implements IDao<Producto> {

    @Override
    public Optional<Producto> uno(Object id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Producto> todos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void guardar(Producto t) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void modificar(Producto t, Object[] params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar(Producto t) {
        // TODO Auto-generated method stub

    }
    
}
