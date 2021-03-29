package org.hua.dit.oopii_21950_219113.entitys;

import java.io.IOException;

public class YoungTraveller extends Traveller
{

    /* CONSTRUCTORS START */

    /**
     *
     * @param age
     * @param name
     * @param cityName
     * @param county
     * @param cafe
     * @param sea
     * @param museums
     * @param restaurants
     * @param stadiums
     * @param mountains
     * @param hotel
     * @param metro
     * @param bars
     * @param sun
     * @throws IOException
     */
    public YoungTraveller(int age, String name, String cityName, String county, int cafe, int sea, int museums, int restaurants, int stadiums, int mountains, int hotel, int metro, int bars, int sun) throws IOException {
        super(age, name, cityName, county, cafe, sea, museums, restaurants, stadiums, mountains, hotel, metro, bars, sun);
    }

    /* CONSTRUCTORS END */

    /**
     * Implemeting the logic for calculating how suitable is a city for a client based on his preferences and how we calculate it for a
     * young clinet.
     *
     * @param city
     * @return Value between 0(min) and 1(max).
     */
    @Override
    public double similarityTermVector(City city)
    {
        int[] cityTermVectorVector = city.getTermVector();
        int[] travellerTermVector = getTermVector();
        double[] cityGeodesicVector = city.getGeodesicVector();
        double[] travellerGeodesicVector = getGeodesicVector();
        double finalResult=0;
        int intResult=0;
        for(int i=0;i<10;i++)
        {
            //that's the Σ((user(i)-city(i))
            intResult+=(travellerTermVector[i]-cityTermVectorVector[i])*(travellerTermVector[i]-cityTermVectorVector[i]);
        }
        finalResult=1/(1+Math.sqrt(intResult));
        return finalResult;
    }

    /**
     * Implementing the logic how we calculate the final suitability value for the client and the city.
     *
     * @param city
     * @return Value between 0(min) and 1(max).
     */
    @Override
    public double calculate_similarity(City city) {
        //similarity (user,city) = p*similarity_terms_vector () + (1-p)  similarity_geodesic_vector ()
        double p=0.95;
        double firstParametre;
        double secondParametre;
        firstParametre=p*similarityTermVector(city);
        secondParametre=(1-p)*similarityGeodesicVector(city);
        return firstParametre+secondParametre;
    }

}