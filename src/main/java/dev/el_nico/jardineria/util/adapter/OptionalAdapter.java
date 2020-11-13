package dev.el_nico.jardineria.util.adapter;

import java.io.IOException;
import java.util.Optional;

import javax.sound.sampled.AudioFileFormat.Type;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class OptionalAdapter<T> extends TypeAdapter<Optional<T>> {

    private Class<T> tipo_del_optional;

    private OptionalAdapter(Class<T> T) {
        super();
        tipo_del_optional = T;
    }

    @Override
    public void write(JsonWriter out, Optional<T> value) throws IOException {
        // TODO Auto-generated method stub
    
    }

    @Override
    public Optional<T> read(JsonReader in) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public static <T> OptionalAdapter<T> crear(Class<T> optional_of_T) {
        return new OptionalAdapter<>(optional_of_T);
    }

}
