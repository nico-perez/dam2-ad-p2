package dev.el_nico.jardineria.dao.gson;

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
import com.google.gson.GsonBuilder;

import dev.el_nico.jardineria.dao.IDao;
import dev.el_nico.jardineria.excepciones.ExcepcionClienteDuplicado;
import dev.el_nico.jardineria.excepciones.ExcepcionCodigoYaExistente;
import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.excepciones.ExcepcionFormatoIncorrecto;
import dev.el_nico.jardineria.modelo.Cliente;
import dev.el_nico.jardineria.util.adapter.OptionalAdapterFactory;

public class ClientesGsonDao implements IDao<Cliente> {

    private File archivo_json;

    private SortedMap<Integer, Cliente> clientes = new TreeMap<>();

    public ClientesGsonDao(String ruta_archivo_json) {

        archivo_json = new File(ruta_archivo_json);
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
            // Deserializa el array leído del json

            // usando el adapter jejejeje
            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OptionalAdapterFactory()).create();
            Cliente[] arrayClientes = gson.fromJson(contenido_json, Cliente[].class);
            if (arrayClientes != null) {
                for (int i = 0; i < arrayClientes.length; ++i) {
                    try {
                        // Asegúrase de que el cliente deserializado es válido, 
                        // ya que se podría haber escrito algún valor sin sentido
                        // con un editor de texto, por ejemplo.
                        Cliente chequeado = new Cliente.Builder(arrayClientes[i]).build();
                        clientes.put(chequeado.get_codigo(), chequeado);
                    } catch (ExcepcionDatoNoValido e) {
                        // Si el cliente leído no es válido, lo descarta directamente.
                        System.err.println("Un cliente deserializado no tenía datos correctos");
                    } catch (ExcepcionFormatoIncorrecto e) {
                        System.err.println("formato incorrecto! en email, dni o nie");
                    }
                }
            }
        }
    }

    @Override
    public Optional<Cliente> uno(Object id) {
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

                // Lo mismo que en ClientesDao. Debería hacer una
                // función separada para esto.
                throw new ExcepcionClienteDuplicado("Nombre, apellidos y teléfono coinciden con " +
                                                    "los de otro cliente. ¿Entrada duplicada?");                        
            } else {
                throw new ExcepcionCodigoYaExistente("El código de cliente ya existe en la base de datos.");
            }
        } else {

            // Si el cliente es válido, sobrescribe el archivo json

            clientes.put(cliente.get_codigo(), cliente);
            
            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OptionalAdapterFactory()).create();

            String json_clientes = gson.toJson(todos());
            try (FileOutputStream fos = new FileOutputStream(archivo_json)) {
                fos.write(json_clientes.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void modificar(Cliente t, Object[] params) {
        // TODO Auto-generated method stub
        // jeje
    }

    @Override
    public void eliminar(Cliente t) {
        // TODO Auto-generated method stub
        // wop wop
    }
}
