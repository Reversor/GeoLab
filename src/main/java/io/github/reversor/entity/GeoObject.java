package io.github.reversor.entity;

import javax.persistence.Entity;

@Entity
public class GeoObject {

    String name;
    Double latitude;
    Double longitude;
    String country;
    Object photo;
    String description;

}
