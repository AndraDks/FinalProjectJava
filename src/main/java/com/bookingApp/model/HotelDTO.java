package com.bookingApp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelDTO {
    private String countryName;
    private String cityName;
    private String hotelName;
    private double pricePerNight;
    private boolean hasAllInclusive;

    private String oldName;

    // new hotel
    public HotelDTO(String countryName, String cityName, String hotelName, double pricePerNight, boolean hasAllInclusive, String oldName) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.hotelName = hotelName;
        this.pricePerNight = pricePerNight;
        this.hasAllInclusive = hasAllInclusive;
        this.oldName = oldName;
    }

    public HotelDTO() {

    }

    public HotelDTO(String france, String paris, String eiffelHotel, int i, boolean b) {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
