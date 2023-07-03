package src.service;

import src.data.Application;
import src.data.Phone;

import java.util.Map;
import java.util.stream.Collectors;

public class PhoneStorageService {
    private final Phone phone;

    public PhoneStorageService(Phone phone) {
        this.phone = phone;
    }

    public Map<String,Double> getApplicationsStorages() {
        return phone.getApplications().stream().collect(Collectors.toMap(Application::getName, Application::getSize));
    }

    public double getFreeSpace() {
        return phone.getStorage() - phone.getUsedStorage();
    }

    public double getFreeSpacePercentage(){
        return getFreeSpace() / phone.getStorage() * 100;
    }

    public double getUsedSpacePercentage(){
        return phone.getUsedStorage() / phone.getStorage() * 100;
    }

}
