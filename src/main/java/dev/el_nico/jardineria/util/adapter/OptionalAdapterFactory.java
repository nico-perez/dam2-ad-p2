package dev.el_nico.jardineria.util.adapter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Esto es para que el java no se queje de «illegal reflective access operation»
 * al serializar/deserializar objetos Optional<?>
 */
public class OptionalAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        
        Type tipo_posible_optional = type.getType();
        if (type.getRawType() == Optional.class && tipo_posible_optional instanceof ParameterizedType) {
            // El tipo es un Optional parametrizado
            Type tipo_T_del_optional = ((ParameterizedType) tipo_posible_optional).getActualTypeArguments()[0];

            Class<?> clase = tipo_T_del_optional.getClass();
                
        

            OptionalAdapter.crear(clase);



        } else {
            return null;
        }
    }

}
