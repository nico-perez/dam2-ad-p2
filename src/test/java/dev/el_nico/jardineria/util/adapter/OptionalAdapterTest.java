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
            return "" +  i +'\n' + opt +'\n' + otro +'\n' + vacio +'\n' + dob;
        }
    }

    @Test
    public void rest() {
        
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OptionalAdapterFactory()).create();

        String json = gson.toJson(new TestClass());
        System.out.println(json);

        TestClass t = gson.fromJson(json, TestClass.class);
        System.out.println(t);
    }
}
