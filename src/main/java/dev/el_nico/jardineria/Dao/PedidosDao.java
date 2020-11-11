package dev.el_nico.jardineria.Dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import dev.el_nico.jardineria.Excepciones.ExcepcionCodigoYaExistente;
import dev.el_nico.jardineria.Excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.Modelo.Cliente;
import dev.el_nico.jardineria.Modelo.Pedido;

public class PedidosDao implements IDataAccessObject<Pedido> {

    private SortedMap<Integer, Pedido> pedidos = new TreeMap<>();
    private IDataAccessObject<Cliente> clientes;

    // No estoy seguro cómo proporcionar acceso a los clientes si no.
    public PedidosDao(IDataAccessObject<Cliente> clientes) {
        this.clientes = clientes;
        try {
            guardar(new Pedido.Builder(1, 4, "enviado", 1).con_comentarios("holaaa").build());
            guardar(new Pedido.Builder(2, 20, "entregado", 3).con_fecha_de_entrega(new Calendar.Builder().setDate(2020, 11, 25).build()).build());
            guardar(new Pedido.Builder(3, 3, "tal", 2).build());
            guardar(new Pedido.Builder(4, 5, "dddddd", 4).build());
            guardar(new Pedido.Builder(5, 4, "fuego", 1).build());
        } catch (Exception e) {
            System.err.println("Fallo en guardar cliente.");
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Pedido> uno(int id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public List<Pedido> todos() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public void guardar(Pedido t) throws ExcepcionDatoNoValido, 
                                         ExcepcionCodigoYaExistente {
        boolean valido = true;

        // Se asegura de que el código de cliente referencia un
        // cliente existente.
        if (clientes.uno(t.get_codigo_cliente()).isEmpty()) {
            valido = false;
            throw new ExcepcionDatoNoValido("El código de cliente no existe.");
        }

        // Se asegura de que el código de pedido no pertenece ya
        // a un pedido existente.
        if (uno(t.get_codigo()).isPresent()) {
            valido = false;
            throw new ExcepcionCodigoYaExistente("El código de pedido no puede estar duplicado.");
        }

        if (valido) {
            pedidos.put(t.get_codigo(), t);
        }
    }

    @Override
    public void modificar(Pedido t, String params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar(Pedido t) {
        pedidos.remove(t.get_codigo());
    }
}
