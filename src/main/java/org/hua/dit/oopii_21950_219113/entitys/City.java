package org.hua.dit.oopii_21950_219113.entitys;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hua.dit.oopii_21950_219113.Dao.CityId;
import org.hua.dit.oopii_21950_219113.Exceptions.NoSuchOpenWeatherCityException;
import org.hua.dit.oopii_21950_219113.Exceptions.NoSuchWikipediaArticleException;
import org.hua.dit.oopii_21950_219113.entitys.weather.OpenWeatherMap;

import javax.persistence.*;
import java.io.IOException;
import java.net.URL;

@Entity
@Table(name = "CITY")
@IdClass(CityId.class)
public class City {

    /**
     * With the help of the CityId class and the annotations of @IdClass & @Id's we create unique composite primary key's
     * for our database.
     */
    @Id
    @Column(nullable = false)
    private String cityName;

    @Id
    @Column(nullable = false)
    private String country;

    private int cafe;
    private int sea;
    private int museums;
    private int restaurants;
    private int stadiums;
    private int mountains;
    private int hotel;
    private int metro;
    private int bars;
    private int sun;
    private Double lat;
    private Double lon;


    @Transient
    //termVector [cafe = 0,sea = 1,museums = 2, restaurants = 3, stadiums = 4, mountains = 5, hotel = 6, metro = 7, bars = 8, sun = 9]
    private int[] termVector = new int[10];
    @Transient
    //geodesicVector [lat = 0 , lon = 0]
    private double[] geodesicVector = new double[2];
    @Transient
    private OpenData openData = new OpenData();
    @Transient
    private String article;
    @Transient
    private Check check = new Check();


    /* CONSTRUCTORS START */

    /**
     * Creating a custom constructor to initialize all the values/criteria the user gave(not for the 1st-3rd deliverables).
     * We also extracting the latitude and longitude of the city from wiki API.
     *
     * @param cityName Name of the city
     * @param country Country code
     * @param cafe How many times the word cafe is referenced in the wiki text for the country
     * @param sea How many times the word sea is referenced in the wiki text for the country
     * @param museums How many times the word museums is referenced in the wiki text for the country
     * @param restaurants How many times the word restaurants is referenced in the wiki text for the country
     * @param stadiums How many times the word stadiums is referenced in the wiki text for the country
     * @param mountains How many times the word mountains is referenced in the wiki text for the country
     * @param hotel How many times the word hotel is referenced in the wiki text for the country
     * @param metro How many times the word metro is referenced in the wiki text for the country
     * @param bars How many times the word bars is referenced in the wiki text for the country
     * @param sun How many times the word sun is referenced in the wiki text for the country
     * @throws IOException Failed or interrupted I/O operation.
     */
    public City(String cityName,String country, int cafe, int sea, int museums, int restaurants, int stadiums,int mountains,int hotel,int metro,int bars,int sun) throws IOException, NoSuchOpenWeatherCityException {

        ObjectMapper mapper = new ObjectMapper();
        OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "," + country + "&APPID=50ff955e0fc989bf2584a87d8a5f266d"), OpenWeatherMap.class);

        //Checking if the API returned us useful iformation or not.
        if ( weather_obj.getCod() != 200){
            throw new NoSuchOpenWeatherCityException(cityName);
        }

        this.lat = weather_obj.getCoord().getLat();
        this.lon = weather_obj.getCoord().getLon();

        this.cityName = cityName.toUpperCase();
        this.country=country;
        this.cafe = cafe;
        this.sea = sea;
        this.museums = museums;
        this.restaurants = restaurants;
        this.stadiums = stadiums;
        this.mountains=mountains;
        this.hotel=hotel;
        this.metro=metro;
        this.bars=bars;
        this.sun=sun;
    }

    /**
     * This is a custom contractor that also handles the task to search and set the features values.
     *
     * @param cityName The name of the city we want to search and find it's features.
     * @param country The country, the city is located at.
     * @throws IOException
     * @throws NoSuchOpenWeatherCityException
     * @throws NoSuchWikipediaArticleException
     */
    public City(String cityName, String country) throws IOException, NoSuchOpenWeatherCityException, NoSuchWikipediaArticleException {

        ObjectMapper mapper = new ObjectMapper();
        OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "," + country + "&APPID=50ff955e0fc989bf2584a87d8a5f266d"), OpenWeatherMap.class);

        //Checking if the API returned us useful iformation or not.
        if ( weather_obj.getCod() != 200){
            throw new NoSuchOpenWeatherCityException(cityName);
        }

        this.lat = weather_obj.getCoord().getLat();
        this.lon = weather_obj.getCoord().getLon();

        this.cityName=cityName.toUpperCase();
        this.country=country;

        this.article= OpenData.RetrieveData(cityName);
        this.cafe= check.checkVectorValue(CountWords.countCriterionfCity(article,"cafe"));
        this.stadiums= check.checkVectorValue(CountWords.countCriterionfCity(article,"stadium"));
        this.museums= check.checkVectorValue(CountWords.countCriterionfCity(article,"museum"));
        this.sea= check.checkVectorValue(CountWords.countCriterionfCity(article,"sea"));
        this.restaurants= check.checkVectorValue(CountWords.countCriterionfCity(article,"restaurant"));
        this.mountains= check.checkVectorValue(CountWords.countCriterionfCity(article,"mountain"));
        this.hotel=check.checkVectorValue(CountWords.countCriterionfCity(article,"hotel"));
        this.metro=check.checkVectorValue(CountWords.countCriterionfCity(article,"metro"));
        this.bars=check.checkVectorValue(CountWords.countCriterionfCity(article,"bar"));
        this.sun=check.checkVectorValue(CountWords.countCriterionfCity(article,"sun"));

    }

    /**
     * The required no arg constructor.
     */
    public City() {

    }

    /* CONSTRUCTORS END */

    /*START GETTERS AND SETTERS FOR termVector*/

    /**
     *
     * @return cafe
     */
    public int getCafe() {
        return this.cafe;
    }

    /**
     *
     * @param cafe setting the number of times the word appeared in the wiki text
     */
    public void setCafe(int cafe) {
        this.cafe = check.checkVectorValue(cafe);
    }

    /**
     *
     * @return sea
     */
    public int getSea() {
        return this.sea;
    }

    /**
     *
     * @param sea setting the number of times the word appeared in the wiki text
     */
    public void setSea(int sea) {
        this.sea = check.checkVectorValue(sea);
    }

    /**
     *
     * @return museums
     */
    public int getMuseums() {
        return this.museums;
    }

    /**
     *
     * @param museums setting the number of times the word appeared in the wiki text
     */
    public void setMuseums(int museums) {
        this.museums = check.checkVectorValue(museums);
    }

    /**
     *
     * @return restaurants
     */
    public int getRestaurants() {
        return this.restaurants;
    }

    /**
     *
     * @param restaurants setting the number of times the word appeared in the wiki text
     */
    public void setRestaurants(int restaurants) {
        this.restaurants = check.checkVectorValue(restaurants);
    }

    /**
     *
     * @return stadiums
     */
    public int getStadiums() {
        return this.stadiums;
    }

    /**
     *
     * @param stadiums setting the number of times the word appeared in the wiki text
     */
    public void setStadiums(int stadiums) {
        this.stadiums = check.checkVectorValue(stadiums);
    }

    /**
     *
     * @return mountains
     */
    public int getMountains() {
        return this.mountains;
    }

    /**
     *
     * @param mountains setting the number of times the word appeared in the wiki text
     */
    public void setMountains(int mountains) {
        this.mountains = check.checkVectorValue(mountains);
    }

    /**
     *
     * @return hotel
     */
    public int getHotel() {
        return this.hotel;
    }

    /**
     *
     * @param hotel setting the number of times the word appeared in the wiki text
     */
    public void setHotel(int hotel) {
        this.hotel = check.checkVectorValue(hotel);
    }

    /**
     *
     * @return metro
     */
    public int getMetro() {
        return this.metro;
    }

    /**
     *
     * @param metro setting the number of times the word appeared in the wiki text
     */
    public void setMetro(int metro) {
        this.metro = check.checkVectorValue(metro);
    }

    /**
     *
     * @return bars
     */
    public int getBars() {
        return this.bars;
    }

    /**
     *
     * @param bars setting the number of times the word appeared in the wiki text
     */
    public void setBars(int bars) {
        this.bars = check.checkVectorValue(bars);
    }

    /**
     *
     * @return sun
     */
    public int getSun() {
        return this.sun;
    }

    /**
     *
     * @param sun setting the number of times the word appeared in the wiki text
     */
    public void setSun(int sun) {
        this.sun = check.checkVectorValue(sun);
    }

    /**
     *
     * @return A vector with metrics for every criteria the user has chosen(setted).
     */
    public int[] getTermVector()
    {
        termVector[0]=getCafe();
        termVector[1]=getSea();
        termVector[2]=getMuseums();
        termVector[3]=getRestaurants();
        termVector[4]=getStadiums();
        termVector[5]=getMountains();
        termVector[6]=getHotel();
        termVector[7]=getMetro();
        termVector[8]=getBars();
        termVector[9]=getSun();
        return termVector;
    }

    /**
     *
     * @param termVector a vector that has all the parametric values of a City object.
     */
    public void setTermVector(int[] termVector) {
        for( int i=0; i < 10; i++){
            if(termVector[i] > 10)
                this.termVector[i] = 10;
            else
                this.termVector[i] = termVector[i];
        }
    }

    /*END GETTERS AND SETTERS FOR termVector*/

    /*START GETTERS AND SETTERS FOR geodesicVector*/

    /**
     *
     * @return lat
     */
    public double getLat() {
        return this.lat;
    }

    /**
     *
     * @param lat latitude of the city
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     *
     * @return lon
     */
    public double getLon() {
        return this.lon;
    }

    /**
     *
     * @param lon longitude of the city
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     *
     * @return A vector with the values of the latitude and longitude(at the respective positions) of the city we have calculated for the uses
     */
    public double[] getGeodesicVector()
    {
        geodesicVector[0]=getLat();
        geodesicVector[1]=getLon();
        return geodesicVector;
    }

    /**
     *
     * @param geodesicVector Setting the users city latitude and longitude(At their respective positions). Of the calculated result.
     */
    public void setGeodesicVector(double[] geodesicVector) {
       this.geodesicVector=geodesicVector;
    }

    /*END GETTERS AND SETTERS FOR geodesicVectorr*/

    /*Start GETTERS AND SETTERS FOR other City object variables*/

    /**
     *
     * @return cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     *
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /*END GETTERS AND SETTERS FOR other City object variables*/




}
