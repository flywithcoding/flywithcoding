package com.example.myapplication;

public class GarageCard {
    private String garageName;
    private String location;
    private String contact;
    private byte[] image;


//    public GarageCard(String garageName, String location, String contact/*, byte[] image*/) {
//        this.garageName = garageName;
//        this.location = location;
//        this.contact = contact;
//        //this.image = image;
//    }

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

    public byte[] getImage() {
        return image;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.image = imageBytes;
    }
}
