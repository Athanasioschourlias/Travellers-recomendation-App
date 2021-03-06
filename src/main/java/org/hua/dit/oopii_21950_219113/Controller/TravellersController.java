package org.hua.dit.oopii_21950_219113.Controller;

import org.hua.dit.oopii_21950_219113.Dao.CityRepository;
import org.hua.dit.oopii_21950_219113.Exceptions.NoSuchCityException;
import org.hua.dit.oopii_21950_219113.Exceptions.NoSuchOpenWeatherCityException;
import org.hua.dit.oopii_21950_219113.Service.CityService;
import org.hua.dit.oopii_21950_219113.Service.TravellersService;
import org.hua.dit.oopii_21950_219113.entitys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Here in the future we will have the main functionality of the web app, for now we hard coded most of it for the 1st
 * deliverable's sake.
 */
//@CrossOrigin(origins= "http://localhost:3000")
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/") //Because we are hard coding data every time we will be showing stats for the same traveller.
public class TravellersController {

    /*
    NOT RECOMENED BY SPRING, for the purposes of this project we can ignore that because this helps us, as a small team
    write and test code faster, also it is an automation which makes the Dependency injection real easy to understand.
     */
    @Autowired
    private final TravellersService travellersService;
    private final CityRepository cityRepository;
    /**
     *
     * @param travellersService initialize the class object with a given TravellerService
     * @param cityRepository
     */
    public TravellersController(TravellersService travellersService, CityRepository cityRepository) {
        this.travellersService = travellersService;
        this.cityRepository=cityRepository;
    }

    @GetMapping(path = "/travellers")
    public ArrayList<Traveller> getAllTravellers() throws InterruptedException {
        return travellersService.getAllTravellers();
    }

    @GetMapping( path = "{name}/bestCity")
    public City findBestCityForTheUser(@PathVariable("name")String name) throws InterruptedException {
        return  travellersService.findBestCityForTheUser(name);
    }

    @GetMapping( path = "{name}/bestCity/collaborate")
    public City findBestCityCollaborating(@PathVariable("name")String name) throws InterruptedException {
        return  travellersService.findBestCityCollaborating(name);
    }

    @GetMapping(path = "{cityName}/{country}/search")
    public City checkCity(@PathVariable("cityName")String cityName,@PathVariable("country")String country){
        try {
            return travellersService.searchCity(cityName,country);
        }catch (NoSuchCityException | InterruptedException e)
        {
            System.out.println("There is no city with this name!");
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping( path = "/addYoungTraveller")
    public String  addNewTraveller(@RequestBody YoungTraveller traveller) throws IOException, InterruptedException {
        return travellersService.addNewTraveller(traveller);
    }

    @PostMapping( path = "/addMiddleTraveller")
    public String  addNewTraveller(@RequestBody MiddleTraveller traveller) throws IOException, InterruptedException {
        return travellersService.addNewTraveller(traveller);
    }

    @PostMapping( path = "/addElderTraveller")
    public String  addNewTraveller(@RequestBody ElderTraveller traveller) throws IOException, InterruptedException {
        return travellersService.addNewTraveller(traveller);
    }

    //TODO: ?????? ???? free ticket ???? ?????????????? ?????? switch case ?????? ?????? 12 ?????????? ?????? ???????????? ?????? ???????? ???????? ???? ???????????? ?????????????????????? ???????? ?????? free ticket (???????? ???? ???????????? ?????? frontend)

    @GetMapping( path = "{cityName}/{country}/freeTicket")
    public Traveller findFreeTicket(@PathVariable("cityName") String FreeCityName , @PathVariable("country") String FreeCountry) throws NoSuchCityException, InterruptedException {
        return travellersService.findFreeTicket(FreeCityName,FreeCountry);
    }

    @GetMapping(path = "{name}/{number}/bestCity" )
    public ArrayList<City> findXBestCities(@PathVariable String name, @PathVariable Integer number) throws InterruptedException {
        return travellersService.findXBestCities(name,number);
    }

}

