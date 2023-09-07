package org.java.nations;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/db_nations";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "Root_root26.";


    private static final String SQL_COUNTRIES = "SELECT countries.country_id as id, countries.name as Country, regions.name as Region, continents.name as Continent\n" +
            "FROM countries \n" +
            "JOIN regions on regions.region_id = countries.region_id\n" +
            "JOIN continents on continents.continent_id = regions.continent_id\n" +
            "WHERE countries.name LIKE ? "+
            "ORDER BY country; ";

    private static final String SQL_COUNTRIES_ID = "SELECT c.name as Country, \n" +
            " l.`language` as Languages, \n" +
            "cs.`year` as `Year`, cs.population as Population, \n" +
            "cs.gdp as GDP \n" +
            "FROM countries c\n" +
            "JOIN country_stats cs on cs.country_id = c.country_id \n" +
            "JOIN country_languages cl on cl.country_id = c.country_id \n" +
            "JOIN languages l on l.language_id = cl.language_id \n" +
            "WHERE c.country_id = ?; ";



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

    try(Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD))
    {

        System.out.println("Insert a string to look for Countries: ");
        String userString = sc.nextLine();


        try(PreparedStatement ps = con.prepareStatement(SQL_COUNTRIES))
        {

            ps.setString(1,"%"+userString+"%");

            try(ResultSet rs = ps.executeQuery())
            {
                while(rs.next())
                {
                    int id = rs.getInt("id");
                    String country = rs.getString("Country");
                    String region = rs.getString("Region");
                    String continent = rs.getString("Continent");
                    System.out.print("id: ");
                    System.out.println(id);
                    System.out.print("country: ");
                    System.out.println(country);
                    System.out.print("region: ");
                    System.out.println(region);
                    System.out.print("continent: ");
                    System.out.println(continent);
                    System.out.println();


                }

            }

        }

        System.out.println("Insert the ID of the Country from which you want to retrieve all the datas: ");
        int userIDChoice = Integer.parseInt(sc.nextLine());

        try(PreparedStatement ps = con.prepareStatement(SQL_COUNTRIES_ID))
        {
            ps.setInt(1, userIDChoice);

           try(ResultSet rs = ps.executeQuery())
           {

               if(rs.next())
               {

                   System.out.print("Details for Country: ");
                   String country = rs.getString("Country");
                   System.out.println(country);
                   System.out.print("Languages: ");
                   String languages = rs.getString("Languages");
                   System.out.println(languages);
                   System.out.println("Most recent stats");
                   System.out.print("Year: ");
                   int year = rs.getInt("Year");
                   System.out.println(year);
                   System.out.print("Population: ");
                   int population = rs.getInt("Population");
                   System.out.println(population);
                   System.out.print("GDP: ");
                   int gdp = rs.getInt("GDP");
                   System.out.println(gdp);
                   System.out.println();
               }
               else
               {
                   System.out.println("The ID you provided doesn't exit.");
               }


           }



        }


    }
    catch(SQLException e)
    {


        System.out.println("Unable to connect to the database.");


    }

   sc.close();

    }


}
