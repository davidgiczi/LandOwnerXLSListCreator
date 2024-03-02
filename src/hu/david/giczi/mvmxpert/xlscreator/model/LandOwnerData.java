package hu.david.giczi.mvmxpert.xlscreator.model;

import java.util.ArrayList;
import java.util.List;

public class LandOwnerData {

    private int id;
    private String ownerName;
    private String homeTown;
    private String zipCode;
    private String streetAndHouseNumber;
    private List<String> parcelId;
    private String ownerShip;


    public LandOwnerData() {
        parcelId = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetAndHouseNumber() {
        return streetAndHouseNumber;
    }

    public void setStreetAndHouseNumber(String streetAndHouseNumber) {
        this.streetAndHouseNumber = streetAndHouseNumber;
    }

    public List<String> getParcelId() {
        return parcelId;
    }

    public void setParcelId(List<String> parcelId) {
        this.parcelId = parcelId;
    }

    public String getOwnerShip() {
        return ownerShip;
    }

    public void setOwnerShip(String ownerShip) {
        this.ownerShip = ownerShip;
    }

    @Override
    public String toString() {
        return "LandOwnerData{" +
                "id=" + id +
                ", ownerName='" + ownerName + '\'' +
                ", homeTown='" + homeTown + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", streetAndHouseNumber='" + streetAndHouseNumber + '\'' +
                ", parcelId=" + parcelId +
                ", ownerShip='" + ownerShip + '\'' +
                '}';
    }
}
