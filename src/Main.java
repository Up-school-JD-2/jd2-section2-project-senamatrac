package src;

import src.data.ApplicationStore;
import src.data.Contact;
import src.data.Phone;
import src.data.PhoneManagementSystem;
import src.exception.PhoneAlreadyInSystem;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scNextInt;
        Phone phone = null;

        boolean exit = false;
        System.out.println("Uygulamayı Sonlandırmak için -1.");
        while (!exit) {
            do {
                System.out.println("1- Kullanıcı kayıt");
                System.out.println("2- Kullanıcı giriş");
                System.out.println("3- Admin girişi");
                scNextInt = scanner.nextInt();
                switch (scNextInt) {
                    case 0 -> {
                        System.out.println("1- Kullanıcı kayıt");
                        System.out.println("2- Kullanıcı giriş");
                        System.out.println("3- Admin girişi");
                    }
                    case 1 -> {

                        scanner.nextLine();
                        System.out.print("Telefonun sahibinin ismi: ");
                        String owner = scanner.nextLine();

                        System.out.print("Marka: ");
                        String brand = scanner.nextLine();

                        System.out.print("Model: ");
                        String model = scanner.nextLine();

                        System.out.print("Seri numarası: ");
                        String serialNumber = scanner.nextLine();

                        System.out.print("İşlerim Sistemi: ");
                        String os = scanner.nextLine();

                        System.out.print("Bellek boyutu (GB): ");
                        double size = scanner.nextDouble();

                        phone = new Phone(brand, model, serialNumber, os, size*1000, owner);
                        boolean result ;
                        try {
                            result = PhoneManagementSystem.getInstance().addNewPhone(phone);
                            UIService.showResult(result, result ? "Telefon sisteme kayıt edildi." : "Telefon sisteme kayıt edilemedi.");
                        } catch (PhoneAlreadyInSystem e) {
                            UIService.showFailedMessage(e.getMessage());
                        }

                    }
                    case 2 -> {
                        scanner.nextLine();
                        System.out.print("Kullanıcı ismi: ");
                        String owner = scanner.nextLine();
                        var phoneList = PhoneManagementSystem.getInstance().listOwnerPhoneList(owner).stream().sorted(Comparator.comparing(Phone::getBrand)).toList();

                        for (int i = 0; i < phoneList.size(); i++) {
                            System.out.println((i + 1) + "- Marka: " + phoneList.get(i).getBrand() + " Model: " + phoneList.get(i).getModel());
                        }
                        if (phoneList.size() > 0) {
                            System.out.println("->Açmak istediğiniz telefonun sıra numarasını giriniz: ");
                            boolean inputException = true;
                            do {
                                try {
                                    System.out.print("Seçiminiz: ");
                                    int phoneIndex = scanner.nextInt();
                                    if ((phoneIndex > 0 && phoneIndex < phoneList.size()) || phoneList.get(phoneIndex - 1) != null) {
                                        phone = phoneList.get(phoneIndex - 1);
                                        inputException = false;
                                    } else
                                        System.out.println("Hatalı sıra numarası girdiniz");
                                } catch (InputMismatchException e) {
                                    System.out.println("Hatalı giriş yaptınız.Tekrar giriniz: ");
                                }
                            } while (inputException);
                        }
                    }
                    case 3 -> {
                        System.out.println("1- Kullanıcılar ve telefonları");
                        System.out.println("2- Uygulama mağazası işlemleri");
                        int selected = scanner.nextInt();
                        switch (selected) {
                            case 1 -> {
                                UIService.showAllPhonesInSystem();
                            }
                            case 2 -> {
                                System.out.println("Mağazadaki tüm uygulamalar");
                                var allApplication = UIService.showAllApplicaitonsInStore();
                                System.out.println("1- uygulama silmek için");
                                System.out.println("2- uygulama güncellemek için");
                                selected = scanner.nextInt();

                                if (selected == 1) {
                                    System.out.print("Silinecek uygulama sıra numarası: ");
                                    int appIndex = scanner.nextInt();
                                    if (appIndex > 0 && appIndex <= allApplication.size()) {
                                        boolean result = ApplicationStore.getInstance().unInstallApplicationFromStore(allApplication.get(appIndex - 1));
                                        UIService.showResult(result, result ? "Uygulama mağazadan kaldırıldı." : "Uygulama kaldırılamadı.");
                                    } else {
                                        System.out.println("Yanlış sıra numarası girdiniz.");
                                    }

                                } else if (selected == 2) {
                                    System.out.print("Güncellenecek uygulama sıra numarası: ");
                                    int appIndex = scanner.nextInt();
                                    if (appIndex > 0 && appIndex <= allApplication.size()) {
                                        var app = allApplication.get(appIndex - 1).clone();
                                        System.out.print("Version (" + app.getCurrentVersion() + "): ");
                                        int version = scanner.nextInt();
                                        System.out.print("Size(" + app.getSize() + "MB): ");
                                        double size = scanner.nextDouble();
                                        app.setCurrentVersion(version);
                                        app.setSize(size);
                                        app.setUpdatedOn(LocalDate.now());


                                        boolean result = ApplicationStore.getInstance().updateApplicationToStore(app);
                                        UIService.showResult(result, result ? "Uygulama güncellendi." : "Uygulama güncellenemedi.");
                                    } else {
                                        System.out.println("Yanlış sıra numarası girdiniz.");
                                    }

                                } else
                                    System.out.println("hatalı giriş yaptınız.");
                            }
                        }
                    }
                }

            } while (phone == null);

            do {
                System.out.println(phone.getOwner().toUpperCase() + " ---- " + phone.getSerialNumber() + " numaralı telefonu:");
                boolean inputException = true;
                while (inputException) {
                    try {
                        UIService.showMainMenu();
                        System.out.print("Seçiminiz: ");
                        scNextInt = scanner.nextInt();
                        System.out.println();
                        switch (scNextInt) {
                            case 1 -> {
                                System.out.println("\uD83D\uDCF1 Tüm uygulamalar ―――――――――――――――――――――――――");
                                var applications = phone.getPhoneAppService().listAllApplications()
                                        .stream()
                                        .sorted((app1, app2) -> Boolean.compare(app1.isPreInstalled(), app2.isPreInstalled())).toList();

                                UIService.showListOfAllApplications(applications);
                                UIService.showInnerMenuListOfAllApplications(scanner, phone, applications);
                            }
                            case 2 -> {
                                System.out.println("\uD83D\uDCDD Contacts  ―――――――――――――――――――――――――――――");
                                List<Contact> contacts = phone.getPhoneContactService().listContact();
                                var orderedContacts = contacts.stream().sorted(Comparator.comparing((contact) -> contact.getName().toLowerCase())).toList();
                                for (int i = 0; i < orderedContacts.size(); i++) {
                                    System.out.println("\uD83D\uDCCC " + (i + 1) + " - İsim: " + orderedContacts.get(i).getName() + " Soyisim: " + Optional.ofNullable(orderedContacts.get(i).getSurname()).orElse(" . ") + " \uD83E\uDC52 Numara: " + orderedContacts.get(i).getFormattedPhoneNumber());
                                }

                                UIService.showInnerMenuContacts(scanner, phone, orderedContacts);
                            }
                            case 3 -> {
                                scanner.nextLine();
                                boolean wrongInput = true;
                                while (wrongInput) {
                                    try {
                                        String patternPhoneNumber = "\\d{11}";
                                        String phoneNumber ;
                                        boolean wrongPhoneNumberInput = true;
                                        do {
                                            System.out.print("Telefon numarasını giriniz (11 Haneli olmalı): ");
                                            phoneNumber = scanner.nextLine();
                                            if (Objects.equals(phoneNumber, ""))
                                                System.out.println("Bir numara girmelisiniz");
                                            else if (!Pattern.matches(patternPhoneNumber, phoneNumber))
                                                System.out.println("Hatalı numara girişi yaptınız.");
                                            else {
                                                var currentPhoneNumber = phoneNumber;
                                                var contact = phone.getContacts().stream().filter(c -> Objects.equals(c.getPhoneNumber(), currentPhoneNumber)).findFirst();
                                                contact.ifPresentOrElse(phone.getPhoneContactService()::call, () -> {
                                                    String formatted = String.valueOf(currentPhoneNumber).replaceFirst("(\\d{4})(\\d{3})(\\d+)", "($1)-$2-$3");
                                                    System.out.println(formatted + " is calling...");
                                                    try {
                                                        TimeUnit.SECONDS.sleep(1);
                                                    } catch (InterruptedException e) {
                                                        System.out.println("Ulaşılamıyor...");
                                                    } finally {
                                                        System.out.println("Arama sonlandı.");
                                                    }
                                                });
                                                wrongPhoneNumberInput = false;
                                            }
                                        } while (wrongPhoneNumberInput);

                                        wrongInput = false;
                                    } catch (InputMismatchException e) {
                                        System.out.println("Hatalı numara tuşladınız.");
                                    }
                                }
                            }
                            case 4 -> {
                                System.out.println("\u2699\ufe0f Telefon ayarları ――――――― ");
                                UIService.showSettings(scanner, phone);
                            }
                            case 5 -> {
                                System.out.println("Mağazada ki uygulamalar");
                                var inStoreApplications = UIService.showAllApplicaitonsInStore();
                                UIService.showInnerMemuStore(scanner, phone, inStoreApplications);
                            }
                            case 0 -> phone = null;
                            case -1 -> exit = true;
                        }
                        inputException = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Hatalı giriş yaptınız.Tekrar giriniz. ");
                    }
                }
            } while (phone != null);
        }
    }
}
