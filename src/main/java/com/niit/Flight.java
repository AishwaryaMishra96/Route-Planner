package main.java.com.niit;

import java.io.*;
import java.util.Scanner;

public class Flight {
    private String sourceCity;
    private String destination;
    private int distance;
    private String flightTime;
    private String airFare;

    public Flight(String sourceCity, String destination, int distance, String flightTime, String airFare) {
        this.sourceCity = sourceCity;
        this.destination = destination;
        this.distance = distance;
        this.flightTime = flightTime;
        this.airFare = airFare;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public String getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public String getAirFare() {
        return airFare;
    }

    @Override
    public String toString() {
        return
                sourceCity + " "+
                  destination +" "+
                 distance +" "+
                 flightTime +" "+
                 airFare;
    }
}
