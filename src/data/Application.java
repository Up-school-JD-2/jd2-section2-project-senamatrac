package src.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Application implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final String name;
    private final LocalDate releasedOn;
    private final boolean preInstalled;
    private final String offeredBy;
    private double size;
    private int currentVersion;
    private LocalDate updatedOn;


    public Application(UUID id, String name, LocalDate releasedOn, int currentVersion, double size, boolean preInstalled, String offeredby) {
        this.id = id;
        this.name = name;
        this.releasedOn = releasedOn;
        this.currentVersion = currentVersion;
        this.preInstalled = preInstalled;
        this.updatedOn = releasedOn;
        this.size = size;
        offeredBy = offeredby;
    }

    //region GETTER - SETTER
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOfferedBy() {
        return offeredBy;
    }

    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isPreInstalled() {
        return preInstalled;
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
    //endregion

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
        return currentVersion == that.currentVersion && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currentVersion);
    }


    @Override
    public Application clone() {
        try {
            Application clone = (Application) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
