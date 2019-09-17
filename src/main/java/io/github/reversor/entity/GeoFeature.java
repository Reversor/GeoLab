package io.github.reversor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table
public class GeoFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    Double latitude;

    Double longitude;

    @Column(columnDefinition = "CHAR(2)")
    String country;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    byte[] photo;
    String description;

}
