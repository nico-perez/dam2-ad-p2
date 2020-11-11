package dev.el_nico.jardineria.Dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.Gson;

import dev.el_nico.jardineria.Excepciones.ExcepcionCodigoYaExistente;
import dev.el_nico.jardineria.Excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.Modelo.Cliente;
import dev.el_nico.jardineria.Modelo.Pedido;

public class PedidosGsonDao implements IDataAccessObject<Pedido> {
    private static int version = 1;

    private SortedMap<Integer, Pedido> pedidos = new TreeMap<>();
    private IDataAccessObject<Cliente> clientes;

    public PedidosGsonDao(String ruta_archivo_json, IDataAccessObject<Cliente> clientes) {
        this.clientes = clientes;

        File archivo_json = new File(ruta_archivo_json);
        byte[] contenido_bytes = new byte[(int) archivo_json.length()];
        boolean todo_bien = true;

        try (FileInputStream fis = new FileInputStream(archivo_json)) {
            fis.read(contenido_bytes);
        } catch (FileNotFoundException e) {
            todo_bien = false;
            System.err.println("Archivo no encontrado!!!!!!!!!!");
            e.printStackTrace();
        } catch (IOException e) {
            todo_bien = false;
            System.err.println("Algo ha pasado aquí");
            e.printStackTrace();
        }

        if (todo_bien) {
            String contenido_json = new String(contenido_bytes);
            Pedido[] arrayPedidos = new Gson().fromJson(contenido_json, Pedido[].class);
            for (int i = 0; i < arrayPedidos.length; ++i) {
                try {
                    Pedido chequeado = new Pedido.Builder(arrayPedidos[i]).build();
                    if (clientes.uno(chequeado.get_codigo_cliente()).isPresent()) {
                        pedidos.put(chequeado.get_codigo(), chequeado);
                    } else {
                        throw new ExcepcionDatoNoValido("cliente no existe");
                    }
                } catch (ExcepcionDatoNoValido e) {
                    System.err.println("Un cliente deserializado no tenía datos correctos");
                }
            }
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
    public void guardar(Pedido t) throws ExcepcionCodigoYaExistente, 
                                         ExcepcionDatoNoValido {
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
            Gson gson = new Gson();
            String json_pedidos = gson.toJson(todos());
            try (FileOutputStream fos = new FileOutputStream("pedidos" + (version++) + ".json")) {
                fos.write(json_pedidos.getBytes());
            } catch (FileNotFoundException e) {
                System.err.println("file not found exception");
            } catch (IOException e) {
                System.err.println("io exception");
            }
        }
    }

    @Override
    public void modificar(Pedido t, String params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar(Pedido t) {
        // TODO Auto-generated method stub

    }
}
