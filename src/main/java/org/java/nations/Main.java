package org.java.nations;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/db_nations";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "Root_root26.";


    private static final String SQL_QUERY = "SELECT countries.country_id as id, countries.name as Country, regions.name as Region, continents.name as Continent\n" +
            "FROM countries \n" +
            "JOIN regions on regions.region_id = countries.region_id\n" +
            "JOIN continents on continents.continent_id = regions.continent_id\n" +
            "WHERE countries.name LIKE ? "+
            "ORDER BY country; ";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

    try(Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD))
    {

        System.out.println("Insert your string to start the research ");
        String userString = sc.nextLine();


        try(PreparedStatement ps = con.prepareStatement(SQL_QUERY))
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

    }
    catch(SQLException e)
    {


        System.out.println("Unable to connect to the database.");


    }

   sc.close();

    }


}
