package com.bookingApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String temperature;
    private String conditions;
    @Getter
    @Setter
    private String cityImageUrl;
    @Setter
    @Getter
    private String cityDescription;


    public String getCityImageUrl() {
        return cityImageUrl;
    }

    public void setCityImageUrl(String cityImageUrl) {
        this.cityImageUrl = cityImageUrl;
    }

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hotels;


}
