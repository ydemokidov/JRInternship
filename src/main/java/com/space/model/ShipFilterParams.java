package com.space.model;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipFilterParams implements Specification<Ship> {
    private Long id;
    private String name;
    private String planet;
    private ShipType shipType;
    private Long after;
    private Long before;
    private Boolean isUsed;
    private Double minSpeed;
    private Double maxSpeed;
    private Integer minCrewSize;
    private Integer maxCrewSize;
    private Double minRating;
    private Double maxRating;
    private Integer pageNumber;
    private Integer pageSize;
    private ShipOrder order;

    @Override
    public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(id!=null){
            Predicate idP = criteriaBuilder.equal(root.get("id"), id);
            predicates.add(idP);
        }

        if(name != null){
            Predicate nameP = criteriaBuilder.like(root.get("name"), "%"+this.name+"%");
            predicates.add(nameP);
        }

        if(planet!=null){
            Predicate planetP = criteriaBuilder.like(root.get("planet"), "%"+this.planet+"%");
            predicates.add(planetP);
        }

        if(shipType!=null){
            Predicate shipTypeP = criteriaBuilder.equal(root.get("shipType"),this.shipType.toString());
            predicates.add(shipTypeP);
        }

        if(after !=null){
            Date afterDt = new Date(after);
            Predicate afterP = criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"),afterDt);
            predicates.add(afterP);
        }

        if(before !=null){
            Date beforeDt = new Date(before);
            Predicate beforeP = criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"),beforeDt);
            predicates.add(beforeP);
        }

        if(isUsed!=null){
            Predicate isUsedP = criteriaBuilder.equal(root.get("isUsed"),Boolean.valueOf(this.isUsed));
            predicates.add(isUsedP);
        }

        if(minSpeed!=null){
            Predicate minSpeedP = criteriaBuilder.greaterThanOrEqualTo(root.get("speed"),this.minSpeed);
            predicates.add(minSpeedP);
        }

        if(maxSpeed!=null){
            Predicate maxSpeedP = criteriaBuilder.lessThanOrEqualTo(root.get("speed"),this.maxSpeed);
            predicates.add(maxSpeedP);
        }

        if(minCrewSize!=null){
            Predicate minCrewSizeP = criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"),this.minCrewSize);
            predicates.add(minCrewSizeP);
        }

        if(maxCrewSize!=null){
            Predicate maxCreSizeP = criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"),this.maxCrewSize);
            predicates.add(maxCreSizeP);
        }

        if(minRating!=null){
            Predicate minRatingP = criteriaBuilder.greaterThanOrEqualTo(root.get("rating"),this.minRating);
            predicates.add(minRatingP);
        }

        if(maxRating!=null){
            Predicate maxRatingP = criteriaBuilder.lessThanOrEqualTo(root.get("rating"),this.maxRating);
            predicates.add(maxRatingP);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Long getAfter() {
        return after;
    }

    public void setAfter(Long after) {
        this.after = after;
    }

    public Long getBefore() {
        return before;
    }

    public void setBefore(Long before) {
        this.before = before;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean used) {
        isUsed = used;
    }

    public Double getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(Double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getMinCrewSize() {
        return minCrewSize;
    }

    public void setMinCrewSize(Integer minCrewSize) {
        this.minCrewSize = minCrewSize;
    }

    public Integer getMaxCrewSize() {
        return maxCrewSize;
    }

    public void setMaxCrewSize(Integer maxCrewSize) {
        this.maxCrewSize = maxCrewSize;
    }

    public Double getMinRating() {
        return minRating;
    }

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }

    public Double getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(Double maxRating) {
        this.maxRating = maxRating;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public ShipOrder getOrder() {
        return order;
    }

    public void setOrder(ShipOrder order) {
        this.order = order;
    }
}
