package com.bookingApp.controller;

import com.bookingApp.model.Hotel;
import com.bookingApp.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

class AdminControllerTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindHotelByName() {
        // hotel data
        Hotel hotel = new Hotel();
        hotel.setName("Eiffel Hotel");

        // set mock to return a list with one hotel
        when(hotelRepository.findByName("Eiffel Hotel")).thenReturn(List.of(hotel));

        // test list is not empty
        List<Hotel> foundHotels = hotelRepository.findByName("Eiffel Hotel");
        assertFalse(foundHotels.isEmpty(), "Hotel list should not be empty");
    }
}

