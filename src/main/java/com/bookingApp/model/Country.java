package com.bookingApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Country {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities;


    @Setter
    @Getter
    @ManyToMany(mappedBy = "countries")
    private List<Hotel> hotels;

    public List<City> getCities() {
        return cities;
    }
}

