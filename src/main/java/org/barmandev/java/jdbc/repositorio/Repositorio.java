package org.barmandev.java.jdbc.repositorio;

import java.util.List;

public interface Repositorio<T> {

    // INTEFACE CRUD
    List<T> Listar(); //-> Obtener una lista de tipo T de los productos
    T porId(Long id); // -> Obtener por tipo id
    void guardar(T t); //-> Por parametro t no devuelve nada guardar productos
    void eliminar(Long id); //-> Por parametro id de tipo long no devuelve nada elimina productos
}
