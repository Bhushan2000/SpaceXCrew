 package com.android.codelab.spacexcrew.model;

public class SpaceX {

    private String id;
    private String name;
    private String agency;
    private String wikipedia;
    private String status;
    private String image;


    public SpaceX(String id, String name, String agency, String wikipedia, String status, String image) {
        this.id = id;
        this.name = name;
        this.agency = agency;
        this.wikipedia = wikipedia;
        this.status = status;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
