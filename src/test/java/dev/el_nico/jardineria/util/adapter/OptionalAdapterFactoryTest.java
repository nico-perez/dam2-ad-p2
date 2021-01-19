package dev.el_nico.jardineria.util.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.Test;

import dev.el_nico.jardineria.util.adapter.OptionalAdapterFactoryTest.TestClass.TestEnum;

public class OptionalAdapterFactoryTest {
    
    public static class TestClass {
        enum TestEnum {
            ENUM_ETQ1;
        }

        Optional<Integer> opt_int = Optional.of(69);
        Optional<String> opt_str = Optional.of("string");
        Optional<String> opt_emp = Optional.empty();
        Optional<Double> opt_dou = Optional.of(1.0);
        Optional<Float> opt_flo = Optional.of(1.5f);
        Optional<String> opt_nil = null;
        Optional<TestEnum> opt_enum = Optional.of(TestEnum.ENUM_ETQ1);
    }

    @Test
    public void test() {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new OptionalAdapterFactory()).create();

        String json = gson.toJson(new TestClass());

        final TestClass tc = gson.fromJson(json, TestClass.class);

        assertEquals(69, tc.opt_int.get());
        assertEquals("string", tc.opt_str.get());
        assertEquals(Optional.<String>empty(), tc.opt_emp);
        assertEquals(1.0, tc.opt_dou.get());
        assertEquals(1.5f, tc.opt_flo.get());
        assertEquals(null, tc.opt_nil);
        assertEquals(TestEnum.ENUM_ETQ1, tc.opt_enum.get());
    }
}
