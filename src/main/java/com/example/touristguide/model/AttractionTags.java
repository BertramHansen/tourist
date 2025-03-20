package com.example.touristguide.model;

public enum AttractionTags {

    HISTORICAL("Historical"),
    CULTURAL("Cultural"),
    NATURE("Nature"),
    SHOPPING("Shopping"),
    FOOD("Food"),
    ADVENTURE("Adventure"),
    RELIGIOUS("Religious"),
    MUSEUM("Museum"),
    PARK("Park"),
    LANDMARK("Landmark"),
    ENTERTAINMENT("Entertainment"),
    NIGHTLIFE("Nightlife"),
    BEACH("Beach"),
    TEMPLE("Temple"),
    CASTLE("Castle"),
    MODERN("Modern"),
    TRADITIONAL("Traditional"),
    SCENIC("Scenic"),
    FESTIVAL("Festival"),
    HOTSPRING("Hot spring")
    ;


    private String displayName;

    AttractionTags(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
