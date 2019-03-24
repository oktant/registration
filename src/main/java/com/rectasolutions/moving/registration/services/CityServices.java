package com.rectasolutions.moving.registration.services;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 30-Jul-18.
 */
@Service
public class CityServices {
    @Value("${recta.geoNames.user}")
    private String geoNameUser;

    public ResponseEntity<List<String>> getCities(String countryCode) throws Exception{
        List<String> citiesList=new ArrayList<>();

        WebService.setUserName(geoNameUser);
        ToponymSearchCriteria searchCriteria= new ToponymSearchCriteria();
        searchCriteria.setCountryCode(countryCode);
        ToponymSearchResult searchResult = WebService.search(searchCriteria);
        for (Toponym toponym : searchResult.getToponyms()) {
            citiesList.add(toponym.getName());
            //System.out.println(toponym.getName()+" "+ toponym.getCountryName());
        }
        return new ResponseEntity(citiesList,HttpStatus.OK);

    }
}
