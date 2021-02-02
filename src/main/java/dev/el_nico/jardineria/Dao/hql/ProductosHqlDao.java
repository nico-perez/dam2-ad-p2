package dev.el_nico.jardineria.dao.hql;

import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import dev.el_nico.jardineria.dao.IDao;
import dev.el_nico.jardineria.excepciones.ExcepcionCodigoYaExistente;
import dev.el_nico.jardineria.modelo.Producto;

public class ProductosHqlDao implements IDao<Producto> {

    private Session session;

    public ProductosHqlDao(Session session) {
        this.session = session;
    }

    @Override
    public Optional<Producto> uno(Object id) {
        return Optional.ofNullable(session.get(Producto.class, (String) id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Producto> todos() {
        return session.createQuery("from Producto").list();
    }

    @Override
    public void guardar(Producto t) throws Exception {
        try {
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new ExcepcionCodigoYaExistente("ñ");
        }
    }

    @Override
    public void modificar(Producto t) {
        session.beginTransaction();
        session.evict(t);
        session.update(t);
        session.getTransaction().commit();
    }

    @Override
    public void eliminar(Producto t) {
        session.delete(t);
    }
    
}
