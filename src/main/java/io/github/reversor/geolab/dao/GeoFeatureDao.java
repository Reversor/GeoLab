package io.github.reversor.geolab.dao;

public interface GeoFeatureDao {

    Integer create(GeoFeatureDao entity);

    GeoFeatureDao read(Integer id);

    void update(GeoFeatureDao entity);

    void delete(GeoFeatureDao entity);
}
