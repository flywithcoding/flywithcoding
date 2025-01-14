package com.example.myapplication;

public class GarageCard {
    private String garageName;
    private String location;
    private String contact;


    public GarageCard(String garageName, String location, String contact) {
        this.garageName = garageName;
        this.location = location;
        this.contact = contact;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

//    @Override
//    public String toString() {
//        return
//    }


}
