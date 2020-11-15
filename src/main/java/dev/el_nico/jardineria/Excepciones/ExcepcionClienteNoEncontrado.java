package dev.el_nico.jardineria.excepciones;

public class ExcepcionClienteNoEncontrado extends Exception {

    private static final long serialVersionUID = 5L;

    public ExcepcionClienteNoEncontrado(String msj) {
        super(msj);
    }
    
}
