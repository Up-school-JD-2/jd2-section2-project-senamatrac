package data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Phone {
    private final String brand;
    private final String model;
    private final String serialNumber;
    private final String operationSystem;
    private Double storage;

    private String owner;

    private Set<Application> applications;

    public Phone(String brand, String model, String serialNumber, String operationSystem, Double storage, String owner) {
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.operationSystem = operationSystem;
        this.storage = storage;
        this.owner =  owner;

        applications = new HashSet<>();
        var preInstalledApplications = ApplicationStore.getInstance().getApplications().stream().filter(Application::isPreInstalled).collect(Collectors.toSet());
        applications.addAll(preInstalledApplications);
    }

    //region GETTER-SETTER
    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public Double getStorage() {
        return storage;
    }

    public void setStorage(Double storage) {
        this.storage = storage;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    //endregion

    public void installAnApplication(){}
    public void updateAnApplication(){}
    public void uninstallAnApplication(){}
    public Set<Application> listAllApplications(){
        return applications;
    }
    public void listNotPreInstalledApplications(){}
}
