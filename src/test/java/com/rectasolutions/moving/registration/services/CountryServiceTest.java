package com.rectasolutions.moving.registration.services;

import com.rectasolutions.moving.registration.entities.Country;
import com.rectasolutions.moving.registration.exceptions.CountryNotFound;
import com.rectasolutions.moving.registration.repositories.CountryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)

public class CountryServiceTest {
    List<Country> countries=new ArrayList<>();
    @Mock
    CountryRepository countryRepositoryMock;
    @InjectMocks
    CountryService countryServiceMock;
    @Before
    public void listCounties(){

        Country country=new Country();
        country.setCountryCode("SE");
        country.setCountryName("Sweden");
        country.setPhoneCode("07777777");
        country.setId(1);
        countries.add(country);
        Country country1=new Country();
        country1.setCountryCode("DK");
        country1.setCountryName("Denmark");
        country1.setPhoneCode("07777777");
        country1.setId(2);

        countries.add(country1);


    }

    @Test
    public void getAlCountries() {

        when(countryRepositoryMock.findAll()).thenReturn(countries);
        assertEquals(1, countryServiceMock.getAlCountries().get(0).getId());
        assertEquals(2, countryServiceMock.getAlCountries().get(1).getId());

    }

    @Test(expected = CountryNotFound.class)
    public void getCountry() {
        Optional<Country> objectList = Optional.of(countries.get(0));

        when(countryRepositoryMock.findById(1)).thenReturn(objectList);
        countryServiceMock.getCountry(3);


    }



}

