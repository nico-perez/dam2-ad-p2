package dev.el_nico.jardineria.util.adapter;

import java.util.Optional;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

public class OptionalAdapterTest {
    
    public static class TestClass {
        int i = 5;
        Optional<Integer> opt = Optional.of(69);
        Optional<String> otro = Optional.of("estringo");
        Optional<String> vacio = Optional.empty();
        double dob = 69.68;
        @Override
        public String toString() {
            return "" + i + opt + otro + vacio + dob;
        }
    }

    private static final String json = "[69,1,3,9]";
    @Test
    public void rest() {
        
        OptionalAdapterFactory fac = new OptionalAdapterFactory();
        fac.create(new Gson(), new TypeToken<Optional<?>>(){});
        fac.create(new Gson(), new TypeToken<Optional>(){});
        fac.create(new Gson(), new TypeToken<String>(){});

        
    }
}
