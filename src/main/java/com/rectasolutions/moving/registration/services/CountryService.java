package com.rectasolutions.moving.registration.services;

import com.rectasolutions.moving.registration.entities.Country;
import com.rectasolutions.moving.registration.exceptions.CountryNotFound;
import com.rectasolutions.moving.registration.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAlCountries(){
        return countryRepository.findAll();
    }

    public Country getCountry(int id){
        Optional<Country> countryOptional= countryRepository.findById(id);

        if(countryOptional.isPresent()){
            return countryOptional.get();

        }
        else {
            throw new CountryNotFound();
        }
    }
}
