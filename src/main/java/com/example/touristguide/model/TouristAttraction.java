package com.example.touristguide.model;

import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {

    private String name;
    private String description;
    private List<AttractionTags> tags;
    private AttractionCity city;

    public TouristAttraction(String name, String description){
        this.name = name;
        this.description = description;
        this.tags = new ArrayList<>();
        this.city = AttractionCity.TOKYO;
    }

    public TouristAttraction(String name, String description, List<AttractionTags> tags){
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.city = AttractionCity.TOKYO;
    }

    public TouristAttraction(String name, String description, List<AttractionTags> tags, AttractionCity city){
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.city = city;

    }

    public TouristAttraction(){

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

    public List<AttractionTags> getTags(){
        return tags;
    }

    public void setTags(List<AttractionTags> tags){
        this.tags = tags;
    }

    public AttractionCity getCity(){
        return city;
    }

    public void setCity(AttractionCity city){
        this.city = city;
    }
}
