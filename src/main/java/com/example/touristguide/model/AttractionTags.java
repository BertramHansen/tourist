package com.example.touristguide.model;

public enum AttractionTags {
    MAD ("Mad"),
    SHOPPING ("Shopping"),
    NATUR ("Natur"),
    MONUMENT("Monument"),
    MUSEUM("Museum"),
    BOERNEVENLIG("Børnevenlig");

    private String displayName;

    AttractionTags(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
