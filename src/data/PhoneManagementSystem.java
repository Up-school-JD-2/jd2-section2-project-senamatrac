package src.data;

import src.exception.PhoneAlreadyInSystem;

import java.util.*;
import java.util.stream.Collectors;

public final class PhoneManagementSystem {

    private static PhoneManagementSystem instance;
    private Set<Phone> phones;

    private PhoneManagementSystem() {
        phones = new HashSet<>();
        phones.add(new Phone("Samsung","A5","1234","Android",124000d,"Sena"));
    }

    public static PhoneManagementSystem getInstance(){
        if(instance == null){
            instance = new PhoneManagementSystem();
        }
        return instance;
    }

    public boolean addNewPhone(Phone phone) throws PhoneAlreadyInSystem {
            if (phones.stream().anyMatch(phone1 -> Objects.equals(phone1.getSerialNumber(),phone.getSerialNumber())))
                throw new PhoneAlreadyInSystem(phone.getSerialNumber());
            else {
                return phones.add(phone);
            }
    }

    public List<Phone> listOwnerPhoneList(String owner) {
        return phones.stream().filter(phone -> Objects.equals(phone.getOwner(), owner)).toList();
    }


    public Map<String, Long> listUserPhoneCount() {
        return phones.stream().collect(Collectors.groupingBy(Phone::getOwner,Collectors.counting()));
    }

    public Map<String, List<Phone>> listUserPhone() {

        return phones.stream().collect(Collectors.groupingBy(Phone::getOwner,Collectors.toList()));
    }
}
