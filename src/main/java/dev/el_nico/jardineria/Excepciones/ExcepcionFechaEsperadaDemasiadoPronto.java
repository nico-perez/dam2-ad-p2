package dev.el_nico.jardineria.excepciones;

public class ExcepcionFechaEsperadaDemasiadoPronto extends Exception {

    private static final long serialVersionUID = 7L;

    public ExcepcionFechaEsperadaDemasiadoPronto(String msj) {
        super(msj);
    }
}
