package dev.el_nico.jardineria.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import dev.el_nico.jardineria.Modelo.Cliente;
import dev.el_nico.jardineria.Excepciones.ExcepcionClienteDuplicado;
import dev.el_nico.jardineria.Excepciones.ExcepcionCodigoYaExistente;

public class ClientesDao implements IDataAccessObject<Cliente> {

    private SortedMap<Integer, Cliente> clientes = new TreeMap<>();

    public ClientesDao() {
        try {
            guardar(new Cliente.Builder(1, "Federico", "111-222-2", "111-222-1", "C/ Falsa 123", "Zaragoza").build());
            guardar(new Cliente.Builder(2, "Saturno", "232-922-0", "232-100-1", "Avda. Solomillo 3", "Zaragoza").build());
            guardar(new Cliente.Builder(3, "Janomina", "112-121-1", "112-122-1", "C/ Y Reme Mortadelo 29", "Zaragoza").con_codigo_postal("303013").build());
            guardar(new Cliente.Builder(4, "Rémora", "111-473-3", "111-222-1", "13 Rue del Percebe", "Zaragoza").con_limite_credito(69.0).con_pais("España").build());
        } catch (Exception e) {
            System.err.println("movidon");
        }
    }

    @Override
    public Optional<Cliente> uno(int id) {
        return Optional.ofNullable(clientes.get(id));
    }

    @Override
    public List<Cliente> todos() {
        return new ArrayList<Cliente>(clientes.values());
    }

    @Override
    public void guardar(Cliente cliente) throws ExcepcionClienteDuplicado, 
                                                ExcepcionCodigoYaExistente {
        // Se asegura de que el cliente no está duplicado.
        Optional<Cliente> otro = uno(cliente.get_codigo());
        if (otro.isPresent()) {
            Cliente.Contacto cto_este = cliente.get_contacto(),
                             cto_otro = otro.get().get_contacto();
            if (cto_otro.nombre().orElse("").equals(cto_este.nombre().orElse("")) &&
                cto_otro.apellido().orElse("").equals(cto_este.apellido().orElse("")) &&
                cto_otro.telefono().equals(cto_este.telefono())) {
                
                // Los campos código, nombre, apellido y teléfono coinciden,
                // así que es un duplicado de un cliente y eso
                throw new ExcepcionClienteDuplicado("Nombre, apellidos y teléfono coinciden con " +
                                                    "los de otro cliente. ¿Entrada duplicada?");                        
            } else {
                // El código de cliente ya existe.
                throw new ExcepcionCodigoYaExistente("El código de cliente ya existe en la base de datos.");
            }
        } else {
            clientes.put(cliente.get_codigo(), cliente);
        }
    }

    @Override
    public void modificar(Cliente t, String params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar(Cliente t) {
        clientes.remove(t.get_codigo());
    }
    
}
