package dev.el_nico.jardineria.dao.hql;

import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import dev.el_nico.jardineria.dao.IDao;
import dev.el_nico.jardineria.excepciones.ExcepcionCodigoYaExistente;
import dev.el_nico.jardineria.modelo.Pedido;

public class PedidosHqlDao implements IDao<Pedido> {

    private Session session;

    public PedidosHqlDao(Session session) {
        this.session = session;
    }

    @Override
    public Optional<Pedido> uno(Object id) {
        return Optional.ofNullable(session.get(Pedido.class, (Integer) id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Pedido> todos() {
        return session.createQuery("from Pedido").list();
    }

    @Override
    public void guardar(Pedido t) throws Exception {
        try {
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new ExcepcionCodigoYaExistente("u");
        }
    }

    @Override
    public void modificar(Pedido t) {
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
    }

    @Override
    public void eliminar(Pedido t) {
        session.delete(t);
    }
    
}
