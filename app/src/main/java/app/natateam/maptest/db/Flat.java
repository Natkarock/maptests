package app.natateam.maptest.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by macbook on 25/07/ 15.
 */
public class Flat extends RealmObject {
    private  long id;
    private  String adress;
    private int room_count;
    private double square;
    private String floor;
    private String cost;
    private String image;
    private boolean is_favour;
    private double lat;
    private double lon;

    public long getId() {
        return id;
    }

    public String getAdress() {
        return adress;
    }

    public int getRoom_count() {
        return room_count;
    }

    public double getSquare() {
        return square;
    }

    public String getFloor() {
        return floor;
    }

    public String getCost() {
        return cost;
    }

    public String getImage() {
        return image;
    }

    public boolean is_favour() {
        return is_favour;
    }

    public double getLat() {
        return lat;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIs_favour(boolean is_favour) {
        this.is_favour = is_favour;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }
}
