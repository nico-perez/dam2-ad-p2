package dev.el_nico.jardineria.util.adapter;

import java.util.Optional;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

public class OptionalAdapterTest {
    
    public static class TestClass {
        int i = 5;
        Optional<Integer> opt_int = Optional.of(69);
        Optional<String> opt_str = Optional.of("estringo");
        Optional<String> opt_emp = Optional.empty();
        Optional<Double> opt_dou = Optional.of(1.0);
        Optional<Float> opt_flo = Optional.of(1.5f);
        Optional<String> opt_nil = null;
        //Optional<Optional<Boolean>> opt_opt_bool = Optional.of(Optional.of(true)); // TODO esto lo serializa mal
        double dob = 69.68;
        @Override
        public String toString() {
            return "" + i +'\n' + opt_int +'\n' + opt_str +'\n' + opt_emp +'\n' + opt_dou+'\n' +opt_flo+'\n'+opt_nil+'\n' /*+opt_opt_bool+'\n'*/+ dob;
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
