package hse.leisure.voter.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Marker {
    /**
     * Номер маркера.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * Широта маркера.
     */
    public double latitude;

    /**
     * Долгота маркера.
     */
    public double longtitude;

    /**
     * Описание маркера.
     */
    private String description;

    /**
     * Конструктор маркера.
     * @param latitude Широта.
     * @param longtitude Доглота.
     * @param desc Описание.
     */
    public Marker(double latitude, double longtitude, String desc) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.description = desc;
    }

    /**
     * Пустой конструктор.
     */
    public Marker() {

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}