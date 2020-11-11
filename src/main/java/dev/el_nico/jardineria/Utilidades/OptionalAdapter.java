package dev.el_nico.jardineria.Utilidades;

import java.io.IOException;
import java.util.Optional;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Esto es para que el java no se queje de «illegal reflective access operation»
 * al serializar/deserializar objetos Optional<T>
 */
public class OptionalAdapter<T> extends TypeAdapter<Optional<T>> {

    @Override
    public void write(JsonWriter out, Optional<T> value) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<T> read(JsonReader in) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
