package com.example.finalproject;

public class Weather {
    private Coord coordinates;
    private double Temp;
    private double WindSpd;
    private double WindChl;
    private String weatherDesc;

    public Weather(Coord coordinates, double temp, double windSpd, double windChl, String weatherDesc) {
        this.coordinates = coordinates;
        Temp = temp;
        WindSpd = windSpd;
        WindChl = windChl;
        this.weatherDesc = weatherDesc;
    }

    public Coord getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coord coordinates) {
        this.coordinates = coordinates;
    }

    public double getTemp() {
        return Temp;
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    public double getWindSpd() {
        return WindSpd;
    }

    public void setWindSpd(double windSpd) {
        WindSpd = windSpd;
    }

    public double getWindChl() {
        return WindChl;
    }

    public void setWindChl(double windChl) {
        WindChl = windChl;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }
}

 class Coord{
    public double latitude;
    public double longitude;

}
