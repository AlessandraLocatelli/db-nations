package org.java.nations;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/db_nations";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "Root_root26.";


    private static final String SQL_COUNTRIES = """
             SELECT countries.country_id as id, countries.name as Country, regions.name as Region, continents.name as Continent
             FROM countries 
             JOIN regions on regions.region_id = countries.region_id 
             JOIN continents on continents.continent_id = regions.continent_id 
             WHERE countries.name LIKE ? 
             ORDER BY countries.name;
             """;

    private static final String SQL_COUNTRY_STAT = """
                         SELECT c.name as Country,
                         l.`language` as Languages,
                         cs.`year` as `Year`, 
                         cs.population as Population,
                         cs.gdp as GDP 
                         FROM countries c
                         JOIN country_stats cs on cs.country_id = c.country_id
                         JOIN country_languages cl on cl.country_id = c.country_id
                         JOIN languages l on l.language_id = cl.language_id
                         WHERE c.country_id = ?;
                          """;


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Search: ");
        String userString = sc.nextLine();

    try(Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)) {


        try (PreparedStatement ps = con.prepareStatement(SQL_COUNTRIES)) {

            ps.setString(1, "%" + userString + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
            catch (SQLException e)
            {
                System.out.println("Unable to execute statement.");
                e.printStackTrace();

            }
        }
        catch (SQLException e)
        {
            System.out.println("Unable to prepare statement.");
            e.printStackTrace();
        }

        System.out.println("Insert the ID of the Country from which you want to retrieve all the data: ");
        int userIDChoice = Integer.parseInt(sc.nextLine());

        try(PreparedStatement ps = con.prepareStatement(SQL_COUNTRY_STAT))
        {
            ps.setInt(1, userIDChoice);

           try(ResultSet rs = ps.executeQuery())
           {

               while(rs.next())
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
                   double gdp = rs.getDouble("GDP");
                   System.out.println(gdp);
                   System.out.println();
               }


           }
           catch (SQLException e)
           {
               System.out.println("Unable to execute statement.");
               e.printStackTrace();

           }
        }
        catch (SQLException e)
        {
            System.out.println("Unable to prepare statement.");
            e.printStackTrace();
        }
    }
    catch(SQLException e)
    {


        System.out.println("Unable to connect to the database.");
        e.printStackTrace();

    }

   sc.close();

    }


}
