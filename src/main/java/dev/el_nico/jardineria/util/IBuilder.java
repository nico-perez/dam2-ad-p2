package dev.el_nico.jardineria.util;

public interface IBuilder<T> {
    /**
     * Esta función debe asegurarse de que todos los
     * datos de la clase siendo buildeada son correctos.
     * Si lo son, devuelve una instancia de la clase T
     * con esos datos. Si no, devuelve null y lanza una
     * excepcion.
     * @return Una instancia válida de T si los datos son
     * correctos. Si no, null.
     */
    public abstract T build() throws Exception;
}
