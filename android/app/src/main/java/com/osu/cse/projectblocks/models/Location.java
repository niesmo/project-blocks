package com.osu.cse.projectblocks.models;

/**
 * Created by niesmo on 11/8/2015.
 */
public class Location {
    private String area;
    private PhysicalAddress physical;
    private Coordinates coordinates;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public PhysicalAddress getPhysical() {
        return physical;
    }

    public void setPhysical(PhysicalAddress physical) {
        this.physical = physical;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
