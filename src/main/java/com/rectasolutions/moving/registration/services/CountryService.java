package com.rectasolutions.moving.registration.services;

import com.rectasolutions.moving.registration.entities.Country;
import com.rectasolutions.moving.registration.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAlCountries(){
        return countryRepository.findAll();
    }

    public Country getCountry(int id){
        return countryRepository.findById(id).get();
    }
}
