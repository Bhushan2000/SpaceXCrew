package com.android.codelab.spacexcrew.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity()
public class CrewEntity {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "agency")
    private String agency;

    @ColumnInfo(name = "wikipedia")
    private String wikipedia;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "imageUrl")
    private String image;

    public CrewEntity(Integer id,  String name, String agency, String wikipedia, String status, String image) {
        this.id = id;

        this.name = name;
        this.agency = agency;
        this.wikipedia = wikipedia;
        this.status = status;
        this.image = image;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
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
