package org.java.nations;

import java.sql.*;

public class Main {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/db_nations";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "Root_root26.";


    private static final String SQL_QUERY = "SELECT countries.country_id as id, countries.name as Country, regions.name as Region, continents.name as Continent\n" +
            "FROM countries \n" +
            "JOIN regions on regions.region_id = countries.region_id\n" +
            "JOIN continents on continents.continent_id = regions.continent_id\n" +
            "ORDER BY country; ";

    public static void main(String[] args) {

    try(Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD))
    {

        try(PreparedStatement ps = con.prepareStatement(SQL_QUERY))
        {

            try(ResultSet rs = ps.executeQuery())
            {
                while(rs.next())
                {
                    int id = rs.getInt(1);
                    String country = rs.getString(2);
                    String region = rs.getString(3);
                    String continent = rs.getString(4);
                    System.out.println("id: "+id);
                    System.out.println("country: "+country);
                    System.out.println("region: "+region);
                    System.out.println("continent: "+continent);


                }

            }




        }






    }
    catch(SQLException e)
    {


        System.out.println("Unable to connect to the database.");


    }



    }


}
