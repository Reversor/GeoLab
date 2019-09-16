package io.github.reversor.dao;

public interface GeoObjectDao {

    Integer create(GeoObjectDao entity);

    GeoObjectDao read(Integer id);

    void update(GeoObjectDao entity);

    void delete(GeoObjectDao entity);
}
