package dev.el_nico.jardineria.excepciones;

public class ExcepcionFechaDeCreacionDePedidoNoValida extends Exception {

    private static final long serialVersionUID = 6L;
    
    public ExcepcionFechaDeCreacionDePedidoNoValida(String msj) {
        super(msj);
    }
}
