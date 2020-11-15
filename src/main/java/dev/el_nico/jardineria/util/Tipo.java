package dev.el_nico.jardineria.util;

import java.lang.reflect.Type;

public class Tipo {
    
    public static boolean numerico(Type T) {
        return T == Byte.class
            || T == Short.class
            || T == Integer.class
            || T == Long.class
            || T == Float.class
            || T == Double.class;
    }

    public static boolean booleano(Type T) {
        return T == Boolean.class;
    }

    public static boolean cadena(Type T) {
        return T == String.class;
    }
}
