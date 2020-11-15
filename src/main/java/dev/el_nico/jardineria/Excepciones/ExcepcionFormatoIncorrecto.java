package dev.el_nico.jardineria.excepciones;

public class ExcepcionFormatoIncorrecto extends Exception {

    private static final long serialVersionUID = 4L;
    
    public ExcepcionFormatoIncorrecto(String msj) {
        super(msj);
    }
}
