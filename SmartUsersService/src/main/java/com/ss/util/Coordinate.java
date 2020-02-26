package com.ss.util;
public class Coordinate{

    private double latitude;
    private double longitude;
    
    public Coordinate (double lat, double longi){
    	latitude=lat;
    	longitude=longi;
    }
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
    
    

}