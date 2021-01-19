package dev.el_nico.jardineria.util.adapter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class OptionalAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        // Comprueba que T es tipo Optional parametrizado
        if (type.getRawType() == Optional.class && type.getType() instanceof ParameterizedType) {

            Type paramType = ((ParameterizedType) type.getType()).getActualTypeArguments()[0];
            TypeToken<?> token = TypeToken.get(paramType);

            TypeAdapter<Object> optTypeTypeAdapter = (TypeAdapter<Object>) gson.getDelegateAdapter(this, token);

            // a lo mejor esto se puede hacer una clase propia para extender y añadir
            // como parametro el tipo generico del optional y asi no tener que usar 
            // raw type pero bueno eso es trabajo para el nico de mañana
            return new TypeAdapter<T>() {

                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    
                    Object paraEscribir = null;

                    if (value != null) {
                        paraEscribir = ((Optional<?>) value).orElse(null);
                    }

                    optTypeTypeAdapter.write(out, paraEscribir);
                }

                @Override
                public T read(JsonReader in) throws IOException {
                    return (T) Optional.of(optTypeTypeAdapter.read(in));
                }
            };
        } else {
            return null;
        }
    }
}
