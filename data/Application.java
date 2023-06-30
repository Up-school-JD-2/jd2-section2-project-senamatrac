package data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Application {

    private final UUID id;
    private final String name;
    private final LocalDate releasedOn;
    private final boolean preInstalled;
    private int currentVersion;
    private LocalDate updatedOn;

    public Application(String name, LocalDate releasedOn,int currentVersion, boolean preInstalled) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.releasedOn = releasedOn;
        this.currentVersion = currentVersion;
        this.preInstalled = preInstalled;
        this.updatedOn = releasedOn;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(int currentVersion) {
        this.currentVersion = currentVersion;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public boolean isPreInstalled() {
        return preInstalled;
    }

    @Override
    public String toString() {
        return name+" {" +
                "id=" + id +
                ", releasedOn=" + releasedOn +
                ", preInstalled=" + preInstalled +
                ", currentVersion=" + currentVersion +
                ", updatedOn=" + updatedOn +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
