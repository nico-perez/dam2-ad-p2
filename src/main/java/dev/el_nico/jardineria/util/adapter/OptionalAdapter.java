package dev.el_nico.jardineria.util.adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Function;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class OptionalAdapter<T> extends TypeAdapter<Optional<T>> {

    private TypeToken<T> tipo_del_optional;
    private Function<JsonReader, Optional<T>> leedorT = (in) -> null;
    private IOException excepcionGuardada;
    private boolean deberiaLanzar;

    private final Function<JsonReader, Optional<T>> leedorInt = (in) -> {
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return Optional.empty();
            } else {
                return (Optional<T>) Optional.of(in.nextInt());
            }
        } catch (IOException e) {
            guardar(e);
        }
        return null;
    };

    private final Function<JsonReader, Optional<T>> leedorLong = (in) -> {
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return Optional.empty();
            } else {
                return (Optional<T>) Optional.of(in.nextLong());
            }
        } catch (IOException e) {
            guardar(e);
        }
        return null;
    };

    private final Function<JsonReader, Optional<T>> leedorBool = (in) -> {
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return Optional.empty();
            } else {
                return (Optional<T>) Optional.of(in.nextBoolean());
            }
        } catch (IOException e) {
            guardar(e);
        }
        return null;
    };

    private final Function<JsonReader, Optional<T>> leedorString = (in) -> {
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return Optional.empty();
            } else {
                return (Optional<T>) Optional.of(in.nextString());
            }
        } catch (IOException e) {
            guardar(e);
        }
        return null;
    };

    private final Function<JsonReader, Optional<T>> leedorDouble = (in) -> {
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return Optional.empty();
            } else {
                return (Optional<T>) Optional.of(in.nextDouble());
            }
        } catch (IOException e) {
            guardar(e);
        }
        return null;
    };

    private void guardar(IOException e) {
        excepcionGuardada = e;
        deberiaLanzar = true;
    }

    private OptionalAdapter(TypeToken<T> T) {
        super();
        tipo_del_optional = T;
    }

    @Override
    public void write(JsonWriter out, Optional<T> value) throws IOException {
        if (value.isPresent()) {
            out.value(value.get().toString());
        } else {
            out.nullValue();
        }
    }

    @Override
    public Optional<T> read(JsonReader in) throws IOException {
        Optional<T> resultado = leedorT.apply(in);
        if (deberiaLanzar) {
            deberiaLanzar = false;
            throw excepcionGuardada;
        } else {
            return resultado;
        }
    }

    public static <T> OptionalAdapter<T> crear(TypeToken<T> typeToken) {

        var adapter = new OptionalAdapter<>(typeToken);
        
        Type T_del_optional = typeToken.getType();
        if (T_del_optional == Integer.class) {
            adapter.leedorT = adapter.leedorInt;
        } else if (T_del_optional == Long.class) {
            adapter.leedorT = adapter.leedorLong;
        } else if (T_del_optional == Boolean.class) {
            adapter.leedorT = adapter.leedorBool;
        } else if (T_del_optional == String.class) {
            adapter.leedorT = adapter.leedorString;
        } else if (T_del_optional == Double.class) {
            adapter.leedorT = adapter.leedorDouble;
        } 
        
        return adapter;
    }

}
