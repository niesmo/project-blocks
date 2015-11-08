package com.osu.cse.projectblocks.models;


/**
 * Created by niesmo on 11/8/2015.
 */
public class Cafeteria extends OrchestrateObject{
    private String name;
    private Location location;
    private String style;
    private HoursOfOperation hoursOfOperation;
    private String about;
    private String phoneNumber;
    private Person operationsManager;

    public Cafeteria(String key) {
        super(key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Person getOperationsManager() {
        return operationsManager;
    }

    public void setOperationsManager(Person operationsManager) {
        this.operationsManager = operationsManager;
    }

    public HoursOfOperation getHoursOfOperation() {
        return hoursOfOperation;
    }

    public void setHoursOfOperation(HoursOfOperation hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
    }
}
