package dev.el_nico.jardineria.excepciones;

public class ExcepcionDatoNoValido extends Exception {
    private static final long serialVersionUID = 1L;

    public ExcepcionDatoNoValido(String msj) {
        super(msj);
    }
}
