package dev.el_nico.jardineria.Excepciones;

public class ExcepcionClienteDuplicado extends Exception {
    private static final long serialVersionUID = 3L;
    
    public ExcepcionClienteDuplicado(String msj) {
        super(msj);
    }
}
