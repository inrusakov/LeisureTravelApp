package hse.leisure.voter.dao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Route {
    /**
     * Номер маршрута.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * Коллеция маркеров в маршруте.
     */
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Marker> markers = new LinkedList<Marker>();

    /**
     * Пустой конструктор.
     */
    public Route() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }

    public void addMarker(double lat, double lon, String desc){
        markers.add(new Marker(lat, lon, desc));
    }
}
