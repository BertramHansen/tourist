package com.example.touristguide.service;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
    this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getAllAttractions(){
        return touristRepository.getAllAttractions();
    }

    public TouristAttraction findAttractionByName(String name, String description) {
        TouristAttraction attraction = touristRepository.findAttractionByName(name);
        if (description != null && description.equals("yes")) {
            return new TouristAttraction(attraction.getName(), attraction.getDescription().toUpperCase());
        }
        return attraction;
    }

    public TouristAttraction addAttraction(TouristAttraction touristAttraction){
        return touristRepository.addTouristAttraction(touristAttraction);
    }

    public String deleteAttraction(String name){
        return touristRepository.deleteAttraction(name);
    }

    public String updateAttraction(String name, TouristAttraction newAttraction){
        return touristRepository.updateAttraction(name, newAttraction);
    }
}
