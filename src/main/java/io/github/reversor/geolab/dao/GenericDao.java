package io.github.reversor.geolab.dao;

import java.io.Serializable;

public interface GenericDao<T, PK extends Serializable> {

    PK create(T entity);

    T read(PK id);

    void update(T entity);

    void delete(T entity);
}
