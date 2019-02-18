package com.example.pidrone;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Barang implements Serializable {
    private String latitude;
    private String longitude;
    private String tinggi;

    public Barang(String getLong, String getLat, String getTinggi){
        longitude = getLong;
        latitude = getLat;
        tinggi = getTinggi;
    }
}

