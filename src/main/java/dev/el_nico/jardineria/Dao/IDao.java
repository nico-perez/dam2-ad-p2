package dev.el_nico.jardineria.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz Data Access Object <T> con métodos para leer uno,
 * leer varios, guardar uno, modificar uno y eliminar uno.
 */
public interface IDao<T> { // DAO
    /**
     * Devuelve un Optional<> del tipo del Dao.
     * @param id Código del objeto a recuperar (normalmente, int o String)
     * @return Un Optional<> con, posiblemente, un objeto dentro.
     */
    Optional<T> uno(Object id);

    /**
     * Devuelve todos los objetos contenidos en el Dao, en forma
     * de lista.
     * @return Una lista con todos los objetos del Dao. La lista
     * puede estar vacía.
     */
    List<T> todos();

    /**
     * Guarda un objeto en el Dao. 
     * @param t El objeto a guardar.
     * @throws Exception Excepción a lanzar si el objeto no es válido.
     */
    void guardar(T t) throws Exception;

    /**
     * TODO No estoy seguro de esta :/
     * @param t
     * @param params
     */
    void modificar(T t);

    /**
     * Elimina un objeto del Dao.
     * @param t Objeto a eliminar.
     */
    void eliminar(T t);
}
