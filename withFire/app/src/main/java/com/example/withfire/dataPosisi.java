package com.example.withfire;

public class dataPosisi {

    public String longitude;
    public String latittude;
    public String tinggi;

    public dataPosisi(String longitude, String latittude, String tinggi) {
        this.longitude = longitude;
        this.latittude = latittude;
        this.tinggi = tinggi;
    }

    public dataPosisi(){

    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatittude() {
        return latittude;
    }

    public void setLatittude(String latittude) {
        this.latittude = latittude;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }
}
