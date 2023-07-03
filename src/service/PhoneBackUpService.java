package src.service;

import src.data.Application;
import src.data.Contact;
import src.data.Phone;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class PhoneBackUpService {
    private final Phone phone;

    public PhoneBackUpService(Phone phone) {
        this.phone = phone;
    }

    public void backupContacts() throws IOException {
        String folderPath = "backups" + File.separator + phone.getSerialNumber();
        Files.createDirectories(Paths.get(folderPath));
        FileOutputStream fos = new FileOutputStream(folderPath + File.separator + "contacts.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(phone.getContacts());
        oos.close();
    }

    public void backupApplications() throws IOException {
        String folderPath = "backups" + File.separator + phone.getSerialNumber();
        Files.createDirectories(Paths.get(folderPath));
        FileOutputStream fos = new FileOutputStream(folderPath + File.separator + "applications.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(phone.getApplications());
        oos.close();
    }

    public void restoreContacts() throws IOException, ClassNotFoundException {
        String folderPath = "backups" + File.separator + phone.getSerialNumber();
        FileInputStream fis = new FileInputStream(folderPath + File.separator + "contacts.txt");
        ObjectInputStream oos = new ObjectInputStream(fis);
        List<Contact> contacts = (List<Contact>) oos.readObject();
        phone.restoreContacts(contacts);
        oos.close();
    }

    public void restoreApplications() throws IOException, ClassNotFoundException {
        String folderPath = "backups" + File.separator + phone.getSerialNumber();
        FileInputStream fis = new FileInputStream(folderPath + File.separator + "applications.txt");
        ObjectInputStream oos = new ObjectInputStream(fis);
        Set<Application> applications = (Set<Application>) oos.readObject();
        phone.restoreApplications(applications);
        oos.close();
    }


}
