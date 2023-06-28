package data;

import java.time.LocalDate;
import java.util.UUID;

public class Application {

    private final UUID id;
    private final String name;
    private final LocalDate releasedOn;

    private String version;
    private LocalDate updatedOn;

    public Application(String name, LocalDate relesadOn, String version, LocalDate updatedOn) {
        id = UUID.randomUUID();
        this.name = name;
        this.releasedOn = relesadOn;
        this.version = version;
        this.updatedOn = updatedOn;
    }

    //region GETTER-SETTER
    public String getName() {
        return name;
    }

    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }
    //endregion


}
