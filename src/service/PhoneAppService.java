package src.service;

import src.data.Application;
import src.data.ApplicationStore;
import src.data.Phone;
import src.exception.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PhoneAppService {
    private final Phone phone;

    public PhoneAppService(Phone phone) {
        this.phone = phone;
    }

    public boolean installApplication(Application storeApplication) throws ApplicationAlreadyInstalledException, PhoneStorageException {
        if (phone.getApplications().stream().anyMatch(inPhone -> Objects.equals(inPhone.getId(), storeApplication.getId()))) {
            throw new ApplicationAlreadyInstalledException(storeApplication.getName());
        }
        if (phone.getUsedStorage() + storeApplication.getSize() > phone.getStorage()) {
            throw new PhoneStorageException();
        }

        return phone.addApplication(storeApplication);
    }

    public boolean updateApplication(Application inPhoneApplication) throws ApplicationAlreadyUpdated, ApplicationNotAvailableAnymore, PhoneStorageException {
        var storeApplicationOptional = ApplicationStore.getInstance().getApplications().stream().filter(storeApplication -> Objects.equals(storeApplication.getId(), inPhoneApplication.getId())).findFirst();
        if (storeApplicationOptional.isPresent()) {
            var storeApplication = storeApplicationOptional.get();
            if (phone.getUsedStorage() - inPhoneApplication.getSize() + storeApplication.getSize() > phone.getStorage()) {
                throw new PhoneStorageException();
            }
            if (storeApplication.getCurrentVersion() == inPhoneApplication.getCurrentVersion()) {
                throw new ApplicationAlreadyUpdated(inPhoneApplication.getName());
            }

            phone.removeApplication(inPhoneApplication);
            phone.addApplication(storeApplication);
            return true;
        } else {
            throw new ApplicationNotAvailableAnymore(inPhoneApplication.getName());
        }
    }

    public boolean uninstallApplication(Application inPhoneApplication) throws ApplicationCannotUninstall {
        if (inPhoneApplication.isPreInstalled()) {
            throw new ApplicationCannotUninstall(inPhoneApplication.getName());
        }
        return phone.removeApplication(inPhoneApplication);
    }

    public Set<Application> listAllApplications() {
        return phone.getApplications();
    }

    public Set<Application> listNotPreInstalledApplications() {
        return phone.getApplications().stream().filter(application -> !application.isPreInstalled()).collect(Collectors.toSet());
    }

}
