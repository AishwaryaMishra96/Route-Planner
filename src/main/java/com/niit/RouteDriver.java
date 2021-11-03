package main.java.com.niit;

import java.io.*;
import java.util.Scanner;

public class RouteDriver {

    public static void main(String[] args) throws IOException {
        RouteDriver routeDriver = new RouteDriver();
        int count = 0;
        try {
            Flight[] array1 = routeDriver.readDataFromFile();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your city name: ");
            String sourceCity = scanner.next();
            System.out.println();
            Flight[] allDirectFlights = routeDriver.showDirectFlights(array1, sourceCity);
            System.out.println("Sorted flights details: ");
            routeDriver.sortDirectFlights(allDirectFlights);
            /*System.out.println("Enter your city name: ");
            sourceCity = scanner.next();
            System.out.println("Enter the city you want to travel to: ");*/
            String destination = "";
            routeDriver.showAllConnections(allDirectFlights,sourceCity, destination);
            /* *//*for (Flight flight : allDirectFlights) {
                if (flight.getFlightTime().isEmpty()) {
                    System.out.println("We are sorry. At this point of time, we do not have any information on flights originating from "+flight.getSourceCity());
                    break;*//*
                } else {
                    System.out.println(flight.getSourceCity() + "   " + flight.getDestination());
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int countOfRecords(String fileName) {
        try {
            int recordCount = 0;
//read and ++ the counter
            String data;
            InputStream inputStream = new FileInputStream("src/main/resources/routes.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //data = bufferedReader.readLine();
            while ((data = bufferedReader.readLine()) != null) {
                recordCount++;
            }
            return recordCount;
        } catch (FileNotFoundException ex) {
            return 0;
        } catch (Exception ex) {
            return 0;
        }
    }

    public Flight[] readDataFromFile() throws IOException {
        try {
            int recordCount = countOfRecords("src/main/resources/routes.csv");

            Flight flight[] = new Flight[recordCount];
            InputStream inputStream = new FileInputStream("src/main/resources/routes.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String data;
            int i = 0;

            String[] routes = new String[5];
            System.out.format("%5s %5s %13s %5s %5s", "From", "To", "Distance(KM)", "Travel time", "Typical fare\n");
            System.out.println("---------------------------------------------");
            while ((data = bufferedReader.readLine()) != null) {
                routes = data.split(",");
                flight[i] = new Flight(routes[0].trim(), routes[1].trim(), Integer.parseInt(routes[2].trim()), routes[3].trim(), routes[4].trim());
                System.out.println(flight[i].getSourceCity() + "   " + flight[i].getDestination() + "   " + flight[i].getDistance() + "   " + flight[i].getFlightTime() + "   " + flight[i].getAirFare());

                i++;
            }
            System.out.println();
            return flight;
        } catch (NullPointerException e) {
            System.out.println(e.toString());
            return null;
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.toString();
            return null;
        }
    }

    public Flight[] showDirectFlights(Flight[] routeInfo, String fromCity) throws IOException {

        int i = 0;
        int count = 0;
        Flight[] allDirectFlights = new Flight[routeInfo.length];
        for (Flight flight : routeInfo) {

            if (flight.getSourceCity().equalsIgnoreCase(fromCity)) {
                allDirectFlights[i] = flight;
                i++;
                count++;
            }

        }
        Flight[] directFlights = new Flight[count];
        System.out.format("%5s %5s %13s %5s %5s", "From", "To", "Distance(KM)", "Travel time", "Typical fare\n");
        System.out.println("---------------------------------------------");
        for (i = 0; i < count; i++) {
            if (allDirectFlights[i] != null) {
                directFlights[i] = allDirectFlights[i];
                System.out.println(directFlights[i].getSourceCity() + "   " + directFlights[i].getDestination() + "   " + directFlights[i].getDistance() + "   " + directFlights[i].getFlightTime() + "   " + directFlights[i].getAirFare());

            } else
                System.out.println("We are sorry. At this point of time, we do not have any information on flights originating from " + allDirectFlights[i].getSourceCity());
        }
        return directFlights;
    }

    void sortDirectFlights(Flight[] directFlights) {
        int i=0;
        int len = directFlights.length;
        //Flight temp[] = new Flight[]{};
       // System.out.println("Sorted Flight Details");
        for ( i = 0; i < len; i++) {
            for (int j = 0; j < len - i-1; j++) {

                // to compare one string with other strings
                if (directFlights[j].getDestination().compareTo(directFlights[j+1].getDestination()) > 0) {
                    // swapping
                    Flight temp = directFlights[j];
                    directFlights[j] = directFlights[j+1];
                    directFlights[j+1] =  temp;
                }
            }
        }
        for(Flight flight:directFlights)
        {
            System.out.println(flight.getSourceCity() + "   " + flight.getDestination() + "   " + flight.getDistance() + "   " + flight.getFlightTime() + "   " + flight.getAirFare());
        }
    }

    void showAllConnections(Flight routeInfo[], String fromCity, String toCity) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Flight[] allDirectFlights = showDirectFlights(routeInfo, fromCity);
        Flight[] indirectFlights = new Flight[allDirectFlights.length];
        Flight[] allFlights = new Flight[routeInfo.length];
        int count=0;
        String temp;
        System.out.println("Enter the source and destination cities:");
        fromCity = scanner.next();
        toCity = scanner.next();
        for(int i=0;i< allDirectFlights.length;i++)
        {
            if(allDirectFlights[i].getDestination().equalsIgnoreCase(toCity))
            {
                indirectFlights[count] = allDirectFlights[i];
                System.out.println(indirectFlights[count]);
                count++;
            }
        }
        System.out.println("Indirect flights:");
        for(int i=0; i< routeInfo.length;i++)
        {
            temp = routeInfo[i].getDestination();
            for(int j=0; i< routeInfo.length;i++)
            {
                if(temp.equalsIgnoreCase(indirectFlights[j].getSourceCity()) && indirectFlights[j].getDestination().equalsIgnoreCase(toCity))
                {
                    allFlights[j]= indirectFlights[j];
                    showAllConnections(routeInfo,temp,toCity);
                }
            }
        }
    }
}

