package src.data;

import src.exception.ApplicationAlreadyInstalledException;
import src.service.PhoneAppService;
import src.service.PhoneBackUpService;
import src.service.PhoneContactService;
import src.service.PhoneStorageService;

import java.time.LocalDate;
import java.util.*;

public class Phone {
    private final String brand;
    private final String model;
    private final String serialNumber;
    private final String operationSystem;
    private final Set<Application> applications;
    private final double storage;
    private double usedStorage;
    private String owner;

    private List<Contact> contacts;

    public Phone(String brand, String model, String serialNumber, String operationSystem, double storage, String owner) {
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.operationSystem = operationSystem;
        this.storage = storage;
        this.owner =  owner;
        this.usedStorage = 0d;
        this.contacts = new ArrayList<>();
        applications = new HashSet<>();

        installDefaultApplications();
    }

    private void installDefaultApplications() {
        addApplication(new Application(UUID.randomUUID(),"Contacts", LocalDate.of(2023,1,1),1,20d,true, brand.toUpperCase()));
        addApplication(new Application(UUID.randomUUID(),"Messages", LocalDate.of(2023,1,4),1,75d,true, brand.toUpperCase()));
        applications.forEach(application -> {
            try {
                ApplicationStore.getInstance().installApplicationToStore(application);
            } catch (ApplicationAlreadyInstalledException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public boolean addApplication(Application application){
        if(this.getApplications().add(application)){
            this.usedStorage += application.getSize();
            return true;
        }
        return false;
    }
    public boolean removeApplication(Application application){
        if (applications.contains(application)) {
            if(this.getApplications().remove(application)) {
                this.usedStorage -= application.getSize();
                return true;
            }
        }
        return false;
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

    public Set<Application> getApplications() {
        return applications;
    }

    public double getStorage() {
        return storage;
    }

    public double getUsedStorage() {
        return usedStorage;
    }

    public void setUsedStorage(double usedStorage) {
        this.usedStorage = usedStorage;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
    //endregion

    public PhoneAppService getPhoneAppService() {
        return new PhoneAppService(this);
    }

    public PhoneContactService getPhoneContactService() {
        return new PhoneContactService(this);
    }

    public PhoneStorageService getPhoneStorageService() {
        return new PhoneStorageService(this);
    }
    public PhoneBackUpService getPhoneBackupService() {
        return new PhoneBackUpService(this);
    }
    @Override
    public String toString() {
        return "Phone{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", operationSystem='" + operationSystem + '\'' +
                ", applications=" + applications +
                ", storage=" + storage +
                ", usedStorage=" + usedStorage +
                ", owner='" + owner + '\'' +
                ", contacts=" + contacts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(serialNumber, phone.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }


    public void restoreContacts(List<Contact> contacts) {
        this.contacts.clear();
        this.contacts.addAll(contacts);
    }
    public void restoreApplications(Set<Application> applications) {
        this.applications.clear();
        this.applications.addAll(applications);
    }
}
