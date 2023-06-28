package data;

public class Phone {
    private final String brand;
    private final String model;
    private final String serialNumber;
    private final String operationSystem;
    private Double storage;

    public Phone(String brand, String model, String serialNumber, String operationSystem, Double storage) {
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.operationSystem = operationSystem;
        this.storage = storage;
    }
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
}
