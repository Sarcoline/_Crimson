package com.crimson.core.dao;

import java.util.List;


public interface BaseDAO<E, PK> {

    void save(E object);

    void delete(E object);

    void update(E object);

    List<E> getAll();

    E getById(PK key);
    


}
