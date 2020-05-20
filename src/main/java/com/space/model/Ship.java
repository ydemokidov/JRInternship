package com.space.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
public class Ship {
    private long id;
    private String name;
    private String planet;
    private String shipType;
    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "planet", nullable = true, length = 50)
    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    @Basic
    @Column(name = "shipType", nullable = false, length = 9)
    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    @Basic
    @Column(name = "prodDate", nullable = false)
    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    @Basic
    @Column(name = "isUsed", nullable = true)
    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean used) {
        isUsed = used;
    }

    @Basic
    @Column(name = "speed", nullable = false, precision = 10, scale =2)
    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Basic
    @Column(name = "crewSize", nullable = false)
    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    @Basic
    @Column(name = "rating", nullable = false, precision = 10, scale =2)
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return id == ship.id &&
                Objects.equals(name, ship.name) &&
                Objects.equals(planet, ship.planet) &&
                Objects.equals(shipType, ship.shipType) &&
                Objects.equals(prodDate, ship.prodDate) &&
                Objects.equals(isUsed, ship.isUsed) &&
                Objects.equals(speed, ship.speed) &&
                Objects.equals(crewSize, ship.crewSize) &&
                Objects.equals(rating, ship.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }

    public boolean validate(){
        if(getName()==null || getName().isEmpty() || getName().length()>50){
            return false;
        }

        if(getPlanet()==null || getPlanet().isEmpty() || getPlanet().length()>50){
            return false;
        }

        if(getSpeed() == null || getSpeed()<0.01 || getSpeed()>0.99){
            return false;
        }

        if(getShipType() == null){
            return false;
        }

        if(prodDate == null){
            return false;
        }else {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, 2800);
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);
            if (getProdDate().before(c.getTime())) {
                return false;
            }
            c.set(Calendar.YEAR, 3019);
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DAY_OF_MONTH, 31);
            if (getProdDate().after(c.getTime())) {
                return false;
            }
        }

        if(getCrewSize() == null || getCrewSize()<1 || getCrewSize()>9999){
            return false;
        }

        return true;
    }

    public void ratingRefresh(){
        double k;
        if(isUsed){
            k=0.5;
        }else{
            k=1;
        }

        int currentYear = 3019;

        Calendar c = Calendar.getInstance();
        c.setTime(prodDate);
        int prodYear = c.get(Calendar.YEAR);

        rating = Math.round((80 * speed * k)/(currentYear-prodYear+1) * 100.0) / 100.0;
    }

    public void incrementShip(Ship s){
        if(s.getName()!= null){
            setName(s.getName());
        }

        if(s.getPlanet()!=null){
            setPlanet(s.getPlanet());
        }

        if(s.getProdDate()!=null){
            setProdDate(s.getProdDate());
        }

        if(s.getShipType()!=null){
            setShipType(s.getShipType());
        }

        if(s.getCrewSize()!=null){
            setCrewSize(s.getCrewSize());
        }

        if(s.getSpeed()!=null){
            setSpeed(s.getSpeed());
        }

        if(s.getIsUsed()!=null){
            setIsUsed(s.getIsUsed());
        }
    }
}
