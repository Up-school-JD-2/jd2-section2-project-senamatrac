package data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public final class ApplicationStore {

    private static ApplicationStore instance;

    private Set<Application> applications;

    private ApplicationStore() {
        applications = new HashSet<>();
        initializeApplications();
    }

    private void initializeApplications() {
        applications.add(new Application("Contacts", LocalDate.of(2023,1,1),1,true));
        applications.add(new Application("Whatsapp", LocalDate.of(2023,1,2),1,false));
        applications.add(new Application("Facebook", LocalDate.of(2023,1,3),1,false));
        applications.add(new Application("Messages", LocalDate.of(2023,1,4),1,true));
    }

    public static synchronized ApplicationStore getInstance(){
        if(instance == null){
            instance = new ApplicationStore();
        }
        return instance;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}
