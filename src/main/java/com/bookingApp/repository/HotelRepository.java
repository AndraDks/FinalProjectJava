package com.bookingApp.repository;

import com.bookingApp.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    //    List<Hotel> findByCity(City city);
    List<Hotel> findByCityId(Long cityId);

    List<Hotel> findByName(String oldName);

}
