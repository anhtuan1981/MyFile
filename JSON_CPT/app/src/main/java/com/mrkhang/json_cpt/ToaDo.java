package com.mrkhang.json_cpt;

/**
 * Created by MrKHANG on 3/21/2018.
 */

public class ToaDo {
    private String Latitude;
    private String Longitude;
    private  String Altitude;

    public ToaDo(String latitude, String longitude, String altitude) {
        Latitude = latitude;
        Longitude = longitude;
        Altitude = altitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getAltitude() {
        return Altitude;
    }

    public void setAltitude(String altitude) {
        Altitude = altitude;
    }
}
