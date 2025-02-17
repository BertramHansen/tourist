package com.example.touristguide.model;

public enum AttractionCity {
    TOKYO("Tokyo"),
    OSAKA("Osaka"),
    KYOTO("Kyoto"),
    YOKOHAMA("Yokohama"),
    NAGOYA("Nagoya"),
    SAPPORO("Sapporo"),
    KOBE("Kobe"),
    FUKUOKA("Fukuoka"),
    KAWASAKI("Kawasaki"),
    SAITAMA("Saitama");

    private String displayName;
    AttractionCity(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
