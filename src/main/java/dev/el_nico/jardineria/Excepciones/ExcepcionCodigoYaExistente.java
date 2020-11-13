package dev.el_nico.jardineria.excepciones;

public class ExcepcionCodigoYaExistente extends Exception {
    private static final long serialVersionUID = 2L;

    public ExcepcionCodigoYaExistente(String msj) {
        super(msj);
    }
}
