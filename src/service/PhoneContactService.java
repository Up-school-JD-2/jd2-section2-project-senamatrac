package src.service;

import src.data.Contact;
import src.data.Phone;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PhoneContactService {
    private final Phone phone;

    public PhoneContactService(Phone phone) {
        this.phone = phone;
    }

    public void addContact(String name, String surname, String phoneNumber, String email) {
        phone.getContacts().add(new Contact(UUID.randomUUID(), name, surname, phoneNumber, email));
    }

    public boolean addContact(Contact contact) {
        return phone.getContacts().add(contact);
    }

    public void updateContact(UUID id, String name, String surname, String phoneNumber, String email) {
        phone.getContacts().stream().filter(contact -> Objects.equals(contact.getId(), id))
                .findFirst()
                .ifPresent(contact -> {
                    if (name != null) contact.setName(name);
                    if (surname != null) contact.setSurname(surname);
                    if (phoneNumber != null) contact.setPhoneNumber(phoneNumber);
                    if (email != null) contact.setEmail(email);
                });

    }

    public boolean updateContact(Contact selectedContact) {
        var inPhoneContact = phone.getContacts().stream().filter(contact -> Objects.equals(contact.getId(), selectedContact.getId())).findFirst();

        if (inPhoneContact.isPresent()){
            inPhoneContact.ifPresent(contact -> {
                contact.setName(selectedContact.getName());
                contact.setSurname(selectedContact.getSurname());
                contact.setPhoneNumber(selectedContact.getPhoneNumber());
                contact.setEmail(selectedContact.getEmail());
            });
            return true;
        }
        return false;

    }

    public boolean deleteContact(Contact contact) {
        return phone.getContacts().remove(contact);
    }


    public List<Contact> listContact() {
        return phone.getContacts();
    }

    public void call(Contact contact) {
        System.out.println(contact.getName() + " " + contact.getFormattedPhoneNumber() + " is calling...");
        try {
            TimeUnit.SECONDS.sleep(1);
            int currentCount = contact.getCallCount();
            contact.setCallCount(currentCount+1);
        } catch (InterruptedException e) {
            System.out.println("Ulaşılamıyor...");
        }finally{
            System.out.println("Arama sonlandı.");
        }
    }


}
