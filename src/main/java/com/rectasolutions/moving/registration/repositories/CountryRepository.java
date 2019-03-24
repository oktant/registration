package com.rectasolutions.moving.registration.repositories;

import com.rectasolutions.moving.registration.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dell on 26-Jul-18.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country,Integer>{
}
