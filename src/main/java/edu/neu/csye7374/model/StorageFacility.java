package edu.neu.csye7374.model;

public class StorageFacility {
    private int storageFacilityId;

    private String storageFacilityName;

    private String storageFacilityLocation;

    public int getStorageFacilityId() {
        return storageFacilityId;
    }

    public void setStorageFacilityId(int storageFacilityId) {
        this.storageFacilityId = storageFacilityId;
    }

    public String getStorageFacilityName() {
        return storageFacilityName;
    }

    public void setStorageFacilityName(String storageFacilityName) {
        this.storageFacilityName = storageFacilityName;
    }

    public String getStorageFacilityLocation() {
        return storageFacilityLocation;
    }

    public void setStorageFacilityLocation(String storageFacilityLocation) {
        this.storageFacilityLocation = storageFacilityLocation;
    }

    @Override
    public String toString() {
        return "StorageFacility{" + "storageFacilityId=" + storageFacilityId + ", storageFacilityName='" + storageFacilityName + '\'' + ", storageFacilityLocation='" + storageFacilityLocation + '\'' + '}';
    }
}
