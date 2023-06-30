package src;

import data.Application;
import data.Phone;

import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello");
        Phone phone = new Phone("Samsung","deneme","0101","Android",62d,"Sena");
        System.out.println(phone.listAllApplications().stream().map(Application::toString).collect(Collectors.toList()));
    }
}
