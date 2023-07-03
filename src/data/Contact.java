package src.data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;

    private int callCount = 0;

    public Contact(UUID id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact(UUID id, String name, String surname, String phoneNumber, String email) {
        this(id, name, phoneNumber);
        this.surname = surname;
        this.email = email;
    }

    //region GETTER - SETTER
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    //endregion

    public String getFormattedPhoneNumber() {
        return String.valueOf(phoneNumber).replaceFirst("(\\d{4})(\\d{3})(\\d+)", "($1)-$2-$3");
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
