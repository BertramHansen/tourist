package com.example.touristguide.model;

import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {

    private String name;
    private String description;
    private List<AttractionTags> tags;

    public TouristAttraction(String name, String description){
        this.name = name;
        this.description = description;
        this.tags = new ArrayList<>();
    }

    public TouristAttraction(String name, String description, List<AttractionTags> tags){
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addTag(AttractionTags tag){
        tags.add(tag);
    }

    public boolean removeTag(AttractionTags tag){
        for(AttractionTags t: tags){
            if(t.equals(tag)){
                tags.remove(tag);
                return true;
            }
        }

        return false;
    }
}
