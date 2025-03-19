package com.example.touristguide.repository;

import com.example.touristguide.model.AttractionCity;
import com.example.touristguide.model.AttractionTags;
import com.example.touristguide.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
   /* @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

*/
    private JdbcTemplate jdbcTemplate;


    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public TouristAttraction getAllAttractions() {
        List<TouristAttraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM touristguidedatabase.touristattractions LEFT JOIN cities ON touristattractions.city = cities.cityID";

/*
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                TouristAttraction attraction = new TouristAttraction(resultSet.getString("attractionName"), resultSet.getString("description"), AttractionCity.valueOf(resultSet.getString("cityName").toUpperCase()));
                attractions.add(attraction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
        //return attractions;
        return jdbcTemplate.queryForObject(sql, TouristAttraction.class);
    }


    public TouristAttraction findAttractionByName(String name){
        String sql = "SELECT touristguidedatabase.touristattractions.*, touristguidedatabase.cities.cityName \n" +
                "FROM touristguidedatabase.touristattractions \n" +
                "LEFT JOIN touristguidedatabase.cities \n" +
                "ON touristguidedatabase.touristattractions.city = touristguidedatabase.cities.cityID \n" +
                "WHERE touristguidedatabase.touristattractions.attractionName = ?\n";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,name);
        TouristAttraction attraction = null;

        if (rowSet.next()) {
            String attractionName = rowSet.getString("attractionName");
            String description = rowSet.getString("description");
            List<AttractionTags> tags = new ArrayList<AttractionTags>(); //TODO: add tag functionality
            AttractionCity city = AttractionCity.valueOf(rowSet.getString("cityName").toUpperCase()); //TODO: actually get the city

            attraction = new TouristAttraction(attractionName, description, tags, city);

            return attraction;
        }
        else{
            System.out.println("FindAttractionByName returned null");
            return null;
        }

    }

    public void addTouristAttraction(TouristAttraction touristAttraction){
        String sql = "INSERT IGNORE INTO touristAttractions (attractionName, description, tagsID, city) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, touristAttraction.getName(), touristAttraction.getDescription(), 1,1); //TODO få city og tags til at virke
    }
    public String deleteAttraction(String name) {
        String sql = "DELETE FROM touristguidedatabase.touristattractions WHERE attractionName = ?";
        jdbcTemplate.update(sql,name);
        return "Delete succes";
    }



    //TODO: ADD tags
    //Fra chatGPT i retrospekt tror jeg det ville være smartere at bruge String frem for Enums
    public String updateAttraction(String name, TouristAttraction newAttraction) {
        // SQL til at finde cityID baseret på ENUM-navnet
        String cityIdQuery = "SELECT cityID FROM touristguidedatabase.cities WHERE cityName = ?";

        // Finder cityID baseret på ENUM værdien
        Integer cityId = jdbcTemplate.queryForObject(cityIdQuery, Integer.class, newAttraction.getCity().name());

        // SQL til at opdatere turistattraktionen
        String sql = "UPDATE touristguidedatabase.touristattractions SET description = ?, city = ? WHERE attractionName = ?";

        // Opdaterer attraktionen med det fundne cityID
        jdbcTemplate.update(sql, newAttraction.getDescription(), cityId, name);

        return "Attraction Updated";
    }

}
