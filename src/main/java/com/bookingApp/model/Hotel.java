package com.bookingApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Hotel {
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
    private Double pricePerNight;

    @Setter
    @Getter
    private boolean hasAllInclusive;

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "hotel_countries",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return this.city;
    }
}
