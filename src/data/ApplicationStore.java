package src.data;

import src.exception.ApplicationAlreadyInstalledException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class ApplicationStore {

    private static ApplicationStore instance;

    private final Set<Application> applications;

    private ApplicationStore() {
        applications = new HashSet<>();
        initializeApplications();
    }

    public static synchronized ApplicationStore getInstance() {
        if (instance == null) {
            instance = new ApplicationStore();
        }
        return instance;
    }

    public Set<Application> getApplications() {
        return applications;
    }


    private void initializeApplications() {
        applications.add(new Application(UUID.randomUUID(), "Whatsapp", LocalDate.of(2022, 12, 2), 1, 120d, false, "Whatsapp LLC"));
        applications.add(new Application(UUID.randomUUID(), "Facebook", LocalDate.of(2022, 1, 13), 1, 220d, false, "Facebook LLC"));
    }

    public boolean installApplicationToStore(Application application) throws ApplicationAlreadyInstalledException {
        var storeApplicationOptional = applications.stream().filter(storeApplication -> Objects.equals(application.getId(), storeApplication.getId())).findFirst();
        if (storeApplicationOptional.isPresent()) {
            throw new ApplicationAlreadyInstalledException(application.getName());
        } else {
            applications.add(application);
            return true;
        }
    }

    public boolean updateApplicationToStore(Application application) {
        var storeApplicationOptional = applications.stream().filter(storeApplication -> Objects.equals(application.getId(), storeApplication.getId())).findFirst();
        if (storeApplicationOptional.isPresent()) {
            var storeApplication = storeApplicationOptional.get();
            if (storeApplication.getCurrentVersion() > application.getCurrentVersion()) {
                return false;
            }
            applications.remove(storeApplication);
            applications.add(application);
            return true;
        } else {
            return false;
        }
    }

    public boolean unInstallApplicationFromStore(Application application) {
        if (applications.stream().anyMatch(app -> app.getId() == application.getId())) {
            applications.remove(application);
            return true;
        }
        return false;
    }


}
