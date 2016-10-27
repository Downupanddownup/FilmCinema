package com.example.hopjs.filmcinema.Data;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class Cinema {
    private String id;
    private String name;
    private String address;
    private String lPrice;
    private String distance;

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getlPrice() {
        return lPrice;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setlPrice(String lPrice) {
        this.lPrice = lPrice;
    }

    public void setName(String name) {
        this.name = name;
    }
}
