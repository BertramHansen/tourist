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
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    private JdbcTemplate jdbcTemplate;


    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<TouristAttraction> getAllAttractions() {
        List<TouristAttraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM touristguidedatabase.touristattractions LEFT JOIN cities ON touristattractions.city = cities.cityID";


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

        return attractions;
    }


    public TouristAttraction findAttractionByName(String name){
        String sql = "SELECT touristguidedatabase.touristattractions.*, touristguidedatabase.cities.cityName \n" +
                "FROM touristguidedatabase.touristattractions \n" +
                "LEFT JOIN touristguidedatabase.cities \n" +
                "ON touristguidedatabase.touristattractions.city = touristguidedatabase.cities.cityID \n" +
                "WHERE touristguidedatabase.touristattractions.attractionName = ?\n";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,name);
        TouristAttraction attraction = null;

        String tagSql = "SELECT * FROM touristattractions\n" +
                "JOIN attractionstotags ON touristattractions.attractionID = attractionstotags.attractionID\n" +
                "JOIN tags ON attractionstotags.tagID = tags.tagID\n" +
                "WHERE touristattractions.attractionName = ? ;";


        if (rowSet.next()) {
            String attractionName = rowSet.getString("attractionName");
            String description = rowSet.getString("description");

            SqlRowSet tagRowSet = jdbcTemplate.queryForRowSet(tagSql,attractionName);
            List<AttractionTags> tags = new ArrayList<AttractionTags>();
            while(tagRowSet.next()){
                tags.add(AttractionTags.valueOf(tagRowSet.getString("tagName").toUpperCase().trim()));
            }
            AttractionCity city = AttractionCity.valueOf(rowSet.getString("cityName").toUpperCase());

            attraction = new TouristAttraction(attractionName, description, tags, city);

            return attraction;
        }
        else{
            System.out.println("FindAttractionByName returned null");
            return null;
        }

    }

    public void addTouristAttraction(TouristAttraction touristAttraction){

        int cityId = getCityId(touristAttraction.getCity().getDisplayName());

        String sql = "INSERT IGNORE INTO touristAttractions (attractionName, description, tagsID, city) VALUES(?,?,?,?)";

        jdbcTemplate.update(sql, touristAttraction.getName(), touristAttraction.getDescription(), 1, cityId);

        addTags(touristAttraction);

    }


    public String deleteAttraction(String name) {

        deleteTags(name);

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

        //handling tags:
        deleteTags(name);
        addTags(newAttraction);

        return "Attraction Updated";
    }

    public int getCityId(String name){
        String cityIdQuery = "SELECT cityID FROM touristguidedatabase.cities WHERE cityName = ?";

        Integer cityId = jdbcTemplate.queryForObject(cityIdQuery, Integer.class, name);

        return cityId;
    }


    private void deleteTags(String name){
        //handling tag relations:
        String getAttractionIdSql = "SELECT attractionID FROM touristguidedatabase.touristattractions WHERE attractionName = ?";
        SqlRowSet attractionIDSet = jdbcTemplate.queryForRowSet(getAttractionIdSql, name);
        attractionIDSet.next();
        int attractionID = attractionIDSet.getInt("attractionID");
        String deleteTagsSql = "DELETE FROM touristguidedatabase.attractionsToTags WHERE attractionID = ?";
        jdbcTemplate.update(deleteTagsSql, attractionID);
    }

    private void addTags(TouristAttraction touristAttraction){
        //adding tags to the many to many table:
        //first we get the attractions ID
        String getAttractionIdSql = "SELECT attractionID FROM touristAttractions WHERE attractionName = ?";
        SqlRowSet attractionIdSet = jdbcTemplate.queryForRowSet(getAttractionIdSql, touristAttraction.getName());
        attractionIdSet.next();
        int attractionID = attractionIdSet.getInt("attractionID");

        String getTagIdSql = "SELECT tagID FROM tags WHERE tagName= ? ";
        String insertTagRelationSql = "INSERT IGNORE into attractionsToTags (attractionID, tagID) VALUES(?,?)";
        for(AttractionTags tag: touristAttraction.getTags()){
            SqlRowSet tagIDRowset = jdbcTemplate.queryForRowSet(getTagIdSql, tag.getDisplayName());
            tagIDRowset.next();
            int tagID = tagIDRowset.getInt("tagID");
            jdbcTemplate.update(insertTagRelationSql, attractionID, tagID);


        }
    }

}
