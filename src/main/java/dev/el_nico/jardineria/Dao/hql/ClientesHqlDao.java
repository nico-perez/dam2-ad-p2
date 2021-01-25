package dev.el_nico.jardineria.dao.hql;

import java.util.List;
import java.util.Optional;

import dev.el_nico.jardineria.dao.IDao;
import dev.el_nico.jardineria.modelo.Cliente;

public class ClientesHqlDao implements IDao<Cliente> {

    @Override
    public Optional<Cliente> uno(Object id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> todos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void guardar(Cliente t) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void modificar(Cliente t, Object[] params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar(Cliente t) {
        // TODO Auto-generated method stub

    }
    
}
