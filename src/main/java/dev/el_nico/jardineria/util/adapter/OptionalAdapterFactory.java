package dev.el_nico.jardineria.util.adapter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Esto es para que el java no se queje de «illegal reflective access operation»
 * al serializar/deserializar objetos Optional<?>
 */
public class OptionalAdapterFactory implements TypeAdapterFactory {

    @SuppressWarnings("unchecked") // ssíííí que puedo convertir OptionalAdapter a TypeAdapter
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        
        Type tipo_posible_optional = type.getType();

        // Comprueba que el tipo es un Optional parametrizado
        if (type.getRawType() == Optional.class && tipo_posible_optional instanceof ParameterizedType) {
            
            Type T = ((ParameterizedType) tipo_posible_optional).getActualTypeArguments()[0];

            if (T == Integer.class || T == Long.class || T == Boolean.class || T == String.class || T == Double.class) {
                return (TypeAdapter<T>) OptionalAdapter.crear(TypeToken.get(T));
            } else {
                return (TypeAdapter<T>) gson.getAdapter(T.getClass());
            }
        } else {
            // Si no lo es, no nos interesa
            return null;
        }
    }

}
