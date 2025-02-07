package com.example.touristguide.repository;

import com.example.touristguide.model.TouristAttraction;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private List<TouristAttraction> attractions = new ArrayList<>();

    TouristAttraction attraction1 = new TouristAttraction("Tokyo Tower", "the japanese Eiffeltower");
    TouristAttraction attraction2 = new TouristAttraction("Fuji", "Mount Fuji is an active stratovolcano located on the Japanese island of Honshu");
    TouristAttraction attraction3 = new TouristAttraction("Nara Park", "better known as the deer park, where you can get close to the animals and even give them treats.");


    public TouristRepository() {
        populateAttractions();
    }


    public List<TouristAttraction> getAllAttractions() {
        return attractions;
    }

    public void setAttractions(List<TouristAttraction> attractions) {
        this.attractions = attractions;
    }

    public void populateAttractions() {
        attractions.add(attraction1);
        attractions.add(attraction2);
        attractions.add(attraction3);
    }

    public TouristAttraction findAttractionByName(String name) {
        TouristAttraction result = null;
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                result = attraction;
            }
        }
        return result;
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        attractions.add(touristAttraction);
        return touristAttraction;
    }

}
