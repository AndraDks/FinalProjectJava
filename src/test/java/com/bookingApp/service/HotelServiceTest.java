package com.bookingApp.service;

import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import com.bookingApp.model.Hotel;
import com.bookingApp.repository.CityRepository;
import com.bookingApp.repository.CountryRepository;
import com.bookingApp.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllHotels() {
        Hotel hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setName("Hotel One");
        Hotel hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setName("Hotel Two");

        when(hotelRepository.findAll()).thenReturn(List.of(hotel1, hotel2));

        List<Hotel> hotels = hotelService.getAllHotels();

        assertEquals(2, hotels.size(), "The number of hotels should be 2.");
        assertEquals("Hotel One", hotels.get(0).getName());
        assertEquals("Hotel Two", hotels.get(1).getName());
    }

    @Test
    public void testAddHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel New");

        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel createdHotel = hotelService.addHotel(hotel);

        assertEquals("Hotel New", createdHotel.getName(), "The hotel name should match.");
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    public void testGetHotelById() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Hotel One");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel foundHotel = hotelService.getHotelById(1L);

        assertEquals("Hotel One", foundHotel.getName(), "The hotel name should match.");
    }

    @Test
    public void testGetHotelById_ThrowsException() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelService.getHotelById(1L);
        });
        assertEquals("Invalid hotel Id:1", exception.getMessage());
    }

    @Test
    public void testUpdateHotelName() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Hotel Old");

        when(hotelRepository.findByName("Hotel Old")).thenReturn(List.of(hotel));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        hotelService.updateHotelName("Hotel Old", "Hotel New");

        assertEquals("Hotel New", hotel.getName(), "The hotel name should be updated.");
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    public void testDeleteHotelAndCheckOrphans() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Hotel To Delete");
        City city = new City();
        city.setId(1L);
        city.setName("City One");
        hotel.setCity(city);
        Country country = new Country();
        country.setId(1L);
        country.setName("Country One");
        city.setCountry(country);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.findByCityId(city.getId())).thenReturn(Collections.emptyList());
        when(cityRepository.findByCountry(country)).thenReturn(Collections.emptyList());

        hotelService.deleteHotelAndCheckOrphans(1L);

        verify(hotelRepository, times(1)).delete(hotel);
        verify(cityRepository, times(1)).delete(city);
        verify(countryRepository, times(1)).delete(country);
    }

    @Test
    public void testDeleteHotel() {
        Long hotelId = 1L;
        hotelService.deleteHotel(hotelId);

        verify(hotelRepository, times(1)).deleteById(hotelId);
    }
}
