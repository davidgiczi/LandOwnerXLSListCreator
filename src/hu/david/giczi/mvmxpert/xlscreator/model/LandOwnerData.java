package hu.david.giczi.mvmxpert.xlscreator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LandOwnerData {

    private String ownerName;
    private String homeTown;
    private String zipCode;
    private String streetAndHouseNumber;
    private final List<String> parcelId;
    private final List<String> ownerShip;


    public LandOwnerData() {
        parcelId = new ArrayList<>();
        ownerShip = new ArrayList<>();
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


    public List<String> getOwnerShip() {
        return ownerShip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LandOwnerData that = (LandOwnerData) o;
        return Objects.equals(ownerName, that.ownerName) &&
                Objects.equals(homeTown, that.homeTown) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(streetAndHouseNumber, that.streetAndHouseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerName, homeTown, zipCode, streetAndHouseNumber);
    }

    @Override
    public String toString() {
        return "LandOwnerData{" +
                "ownerName='" + ownerName + '\'' +
                ", homeTown='" + homeTown + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", streetAndHouseNumber='" + streetAndHouseNumber + '\'' +
                ", parcelId=" + parcelId +
                ", ownerShip='" + ownerShip + '\'' +
                '}';
    }
}
