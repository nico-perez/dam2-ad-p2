package dev.el_nico.jardineria.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import dev.el_nico.jardineria.dao.IDao;
import dev.el_nico.jardineria.modelo.Cliente;
import dev.el_nico.jardineria.modelo.Pedido;

public class PedidosSqlDao implements IDao<Pedido> {
    private Connection conn;
    private IDao<Cliente> clientesDao;

    public PedidosSqlDao(ClientesSqlDao clientesDao) {
        conn = clientesDao.conn;
        this.clientesDao = clientesDao;
    }

    public PedidosSqlDao(Connection conn, IDao<Cliente> clientesDao) {
        this.conn = conn;
        this.clientesDao = clientesDao; 
    }

    @Override
    public Optional<Pedido> uno(Object id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Pedido> todos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void guardar(Pedido t) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void modificar(Pedido t, Object[] params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar(Pedido t) {
        // TODO Auto-generated method stub

    }
    
    private static Optional<Pedido> sacarPedidoDeResultSet(ResultSet sqlQuery) throws SQLException {
        if (sqlQuery != null) {
            int codigo_pedido = sqlQuery.getInt("codigo_pedido");
            Calendar fecha_pedido = new Calendar.Builder().setInstant(sqlQuery.getDate("fecha_pedido")).build();
            Calendar fecha_esperada = new Calendar.Builder().setInstant(sqlQuery.getDate("fecha_esperada")).build();
            Calendar fecha_entrega = new Calendar.Builder().setInstant(sqlQuery.getDate("fecha_entrega")).build();
            String estado = sqlQuery.getString("estado");
            String comentarios = sqlQuery.getString("comentarios");
            int codigo_cliente = sqlQuery.getInt("codigo_cliente");

            Pedido pedido;
            try {
                pedido = new Pedido.Builder(codigo, dias_demora, estado, codigo_cliente) // TODO regulero
            }

        } else {
            return Optional.empty();
        }
    }
}
