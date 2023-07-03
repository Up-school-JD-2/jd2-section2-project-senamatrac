package src;

import src.data.*;
import src.exception.ApplicationCannotUninstall;
import src.exception.ApplicationException;
import src.exception.PhoneStorageException;

import java.util.*;
import java.util.regex.Pattern;

public class UIService {

    public static void showMainMenu() {
        System.out.println("┌───────────────────────────────┐");
        System.out.println("  \uD83D\uDCF1 1 - Tüm uygulamalar");
        System.out.println("  \uD83D\uDCDD 2 - Contacts");
        System.out.println("  \uD83D\uDCDE 3 - Phone");
        System.out.println("  \u2699\ufe0f 4 - Ayarlar ");
        System.out.println("  \ud83d\uddd1️ 5 - Uygulama Mağazası ");
        System.out.println("  \u26ab️ 0- Telefonu kapat ");
        System.out.println("└───────────────────────────────┘");
    }


    public static void showFailedMessage(String message) {
        System.out.println("\u2757 \u001B[31m" + message + "\u001B[0m");
    }

    public static void showSuccessMessage(String message) {
        System.out.println("✅ \u001B[32m" + message + "\u001B[0m");
    }

    public static void showResult(boolean result, String message) {
        if (result)
            showSuccessMessage(message);
        else
            showFailedMessage(message);
    }

    public static void showApplicationInfo(Application selectedApp) {
        System.out.println("Seçilen uygulama bilgileri ⤵ ");
        System.out.println("\tİsmi                : " + selectedApp.getName());
        System.out.println("\tSunan               : " + selectedApp.getOfferedBy());
        System.out.println("\tYayın tarihi        : " + selectedApp.getReleasedOn());
        System.out.println("\tGüncellenme tarihi  : " + selectedApp.getUpdatedOn());
        System.out.println("\tBoyutu              : " + selectedApp.getSize());
        System.out.println("\tVersionu            : " + selectedApp.getCurrentVersion());
        System.out.println("\tÖnceden yüklü       :" + (selectedApp.isPreInstalled() ? "✔" : "✘"));
    }

    public static void showUpdateOrDeleteOptions(Phone phone, Application selectedApp, Scanner scanner) {
        if (selectedApp.isPreInstalled()) {
            System.out.println("\t\u001B[35m┌───────────────────┐\u001B[0m");
            System.out.println("\t\u2753 Güncelle (g):");
            System.out.println("\t\u001B[35m└───────────────────┘\u001B[0m");
        } else {
            System.out.println("\t\u001B[35m┌───────────────────┬───────────────────┐\u001B[0m");
            System.out.println("\t   \u2753Güncelle (g):    \u2753Sil (s): ");
            System.out.println("\t\u001B[35m└───────────────────┴───────────────────┘\u001B[0m");
        }
        System.out.println("   \t\uD83D\uDC49 0- Ana menü");

        String scNextString = scanner.next();

        switch (scNextString) {
            case "g" -> {
                try {
                    phone.getPhoneAppService().updateApplication(selectedApp);
                    showSuccessMessage(selectedApp.getName() + " başarıyla güncellenmiştir.");
                } catch (ApplicationException | PhoneStorageException e) {
                    showFailedMessage(e.getMessage());
                }
            }
            case "s" -> {
                if (!selectedApp.isPreInstalled()) {
                    try {
                        boolean result = phone.getPhoneAppService().uninstallApplication(selectedApp);
                        showResult(result, result ? "Uygulama silindi." : "Uygulama silinemedi.");
                    } catch (ApplicationCannotUninstall e) {
                        showFailedMessage(e.getMessage());
                    }
                }
            }
        }
    }

    public static void showListOfAllApplications(List<Application> applications) {
        for (int i = 0; i < applications.size(); i++) {
            StringBuilder applicationInfo = new StringBuilder();
            if (applications.get(i).isPreInstalled()) {
                applicationInfo.append("\uD83D\uDD38 ").append(i + 1).append(" - ");
            } else
                applicationInfo.append("\uD83D\uDD39 ").append(i + 1).append(" - ");
            applicationInfo.append(applications.get(i).getOfferedBy()).append(" - ").append(applications.get(i).getName()).append(" Size: ").append(applications.get(i).getSize()).append("MB Version: ").append(applications.get(i).getCurrentVersion());
            System.out.println(applicationInfo);
        }
    }

    public static void showInnerMenuListOfAllApplications(Scanner scanner, Phone phone, List<Application> applications) {
        if (applications.size() > 0) {
            System.out.println("\t\u001B[33m┌───────────────────────────────────────────────────────┐\u001B[0m");
            System.out.println("  \t\uD83D\uDCDD Uygulama bilgileri için sıra numarasını giriniz:");
            System.out.println("\t\u001B[33m└───────────────────────────────────────────────────────┘\u001B[0m");
        } else
            System.out.println("Uygulama Mağazasından uygulama indirebilirsiniz.");

        System.out.println("   \t\uD83D\uDC49 0- Ana menü");
        boolean inputException = true;
        while (inputException) {
            try {
                int scNextInt = scanner.nextInt();
                if (scNextInt == 0) {
                    break;
                } else if (applications.size() > 0) {
                    Application selectedApp = applications.get(scNextInt - 1);
                    UIService.showApplicationInfo(selectedApp);
                    UIService.showUpdateOrDeleteOptions(phone, selectedApp, scanner);
                }
                inputException = false;
            } catch (InputMismatchException e) {
                System.out.println("Hatalı giriş yaptınız.Tekrar giriniz: ");
            }
        }
    }


    public static List<Application> showAllApplicaitonsInStore() {
        var inStoreApplications = ApplicationStore.getInstance().getApplications().stream().sorted((app1, app2) -> Boolean.compare(app1.isPreInstalled(), app2.isPreInstalled())).toList();
        for (int i = 0; i < inStoreApplications.size(); i++) {
            Application application = inStoreApplications.get(i);
            StringBuilder applicationInfo = new StringBuilder();
            if (application.isPreInstalled()) {
                applicationInfo.append("\uD83D\uDD38 ");
            } else
                applicationInfo.append("\uD83D\uDD39 ");
            applicationInfo.append(i + 1).append(" ").append(application.getOfferedBy()).append(" - ").append(application.getName()).append(" Size: ").append(application.getSize()).append(" Version: ").append(application.getCurrentVersion());
            System.out.println(applicationInfo);
        }
        return inStoreApplications;
    }

    public static void showSettings(Scanner scanner, Phone phone) {
        boolean inputException = true;
        do {
            try {
                int selected = -1;
                while (selected != 0) {
                    System.out.println("\t\u001B[32m┌──────────────────────────────────────┐\u001B[0m");
                    System.out.println("  \t\uD83D\uDD0D1- Telefon bilgilerini görüntüle");
                    System.out.println("  \t\uD83D\uDD0D2- Bellek görüntüle");
                    System.out.println("  \t\uD83D\uDD0D3- Verileri yedekle");
                    System.out.println("  \t\uD83D\uDD0D4- Verileri geri yükle");
                    System.out.println("\t\u001B[32m└──────────────────────────────────────┘\u001B[0m");
                    System.out.println("   \t\uD83D\uDC49 0- Ana menü");
                    System.out.print("Seçiminiz: ");
                    selected = scanner.nextInt();
                    switch (selected) {
                        case 1 -> {
                            System.out.println("Telefon bilgileri  : ");
                            System.out.println("Seri numarası      : " + phone.getSerialNumber());
                            System.out.println("Marka              : " + phone.getBrand());
                            System.out.println("Model              : " + phone.getModel());
                            System.out.println("İşletim Sistemi    : " + phone.getOperationSystem());
                            System.out.println("Hafıza (GB)        : " + phone.getStorage());
                            System.out.println("Uygulama sayısı    : " + phone.getApplications().size());
                            System.out.println("Contact sayısı     : " + phone.getContacts().size());
                        }
                        case 2 -> {
                            showStorageInfo(phone);
                        }
                        case 3 -> {
                            System.out.println("⌛ Veriler yedekleniyor...");

                            try {
                                phone.getPhoneBackupService().backupApplications();
                                showSuccessMessage("Uygulamalar yedeklendi");
                            } catch (Exception e) {
                                System.out.println("Uygulamalar yedeklenemedi.");
                            }

                            try {
                                phone.getPhoneBackupService().backupContacts();
                                showSuccessMessage("Kişiler yedeklendi");
                            } catch (Exception e) {
                                System.out.println("Kişiler yedeklenemedi.");
                            }
                        }
                        case 4 -> {
                            System.out.println("⌛ Veriler geri yükleniyor...");
                            try {
                                phone.getPhoneBackupService().restoreApplications();
                                showSuccessMessage("Uygulamalar geri yüklendi");
                            } catch (Exception e) {
                                System.out.println("Uygulamalar geri yüklenemedi.");
                            }

                            try {
                                phone.getPhoneBackupService().restoreContacts();
                                showSuccessMessage("Kişiler  geri yüklendi");
                            } catch (Exception e) {
                                System.out.println("Kişiler  geri yüklenemedi.");
                            }
                        }
                    }
                    inputException = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Hatalı giriş yaptınız.Tekrar giriniz: ");
            }
        } while (inputException);
    }

    public static void showStorageInfo(Phone phone) {
        System.out.println("Telefon Hafızası: " + phone.getStorage()+ "MB Kullanılan alan: " + phone.getUsedStorage() + "MB");
        for (int i = 0; i < 50; i += 2) {
            if (i == 0)
                System.out.print("╔");
            System.out.print("═");
        }
        System.out.print("╗\n");
        for (double i = 0; i < 50; i += 2) {
            if (i == 0)
                System.out.print("║");
            if (i < phone.getPhoneStorageService().getUsedSpacePercentage())
                System.out.print("\u001B[31m▋");
            else
                System.out.print(" ");
        }
        System.out.print("\u001B[0m║\n");
        for (int i = 0; i < 50; i += 2) {
            if (i == 0)
                System.out.print("╚");
            System.out.print("═");
        }
        System.out.println("╝");

        System.out.println("Dolu alan:%" + phone.getPhoneStorageService().getUsedSpacePercentage() + " Boş alan:%" + phone.getPhoneStorageService().getFreeSpacePercentage());

        System.out.println("\ud83d\udca5 Hafızayı en çok kullanan uygulamalar: ");
        phone.getPhoneStorageService().getApplicationsStorages().entrySet().stream().sorted(Comparator.comparing(Map.Entry<String, Double>::getValue).reversed()).forEach((mapEtry) -> {
            System.out.println(mapEtry.getKey() + " - " + mapEtry.getValue() + "MB");
        });
    }

    public static void showInnerMenuContacts(Scanner scanner, Phone phone, List<Contact> contacts) {
        boolean inputException = true;
        do {
            try {
                System.out.println("\t\u001B[33m┌──────────────────────────────────────────┐\u001B[0m");
                System.out.println("   \t\u2795 1- Yeni bir kişi ekleyin");
                if (contacts.size() > 0) {
                    System.out.println("   \t\uD83D\uDCDD 2- Bir kişinin detay bilgilerini görüntüle");
                    System.out.println("   \t\ud83d\udcde 3- Bir kişi arayın");
                }
                System.out.println("\t\u001B[33m└──────────────────────────────────────────┘\u001B[0m");
                System.out.println("   \t\uD83D\uDC49 0- Ana menü");
                System.out.print("\tSeçiminiz: ");
                int scNextInt = scanner.nextInt();
                System.out.println();
                switch (scNextInt) {
                    //region 1 - New Contact
                    case 1 -> {
                        Contact contact = getContactInfo(scanner, null);
                        boolean result = phone.getPhoneContactService().addContact(contact);

                        showResult(result, result ? "Kişi kayıt edildi." : "Kişi kayıt edilirken bir hata oluştu");
                    }
                    //endregion

                    //region 2 - Info Contact -> update - delete
                    case 2 -> {
                        if (contacts.size() > 0) {
                            System.out.print("\tDetayını görüntülemek istediğiniz kişinin sıra numarasını giriniz: ");
                            boolean inputContactIndexException = true;
                            while (inputContactIndexException) {
                                scanner.nextLine();
                                try {
                                    int scNextint = scanner.nextInt();
                                    Contact contact = contacts.get(scNextint - 1);
                                    System.out.println("\tİsim:    " + contact.getName());
                                    System.out.println("\tSoyisim: " + contact.getSurname());
                                    System.out.println("\tNumber:  " + contact.getFormattedPhoneNumber());
                                    System.out.println("\tEmail:   " + contact.getEmail());
                                    System.out.println("\tArama geçmişi ⬈: " + contact.getCallCount());

                                    boolean inputOptException = true;
                                    while (inputOptException) {
                                        scanner.nextLine();
                                        try {
                                            System.out.println("\t\t\u001B[35m┌───────────────────┬───────────────────┐\u001B[0m");
                                            System.out.println("\t\t   \u2753Güncelle (g):    \u2753Sil (s): ");
                                            System.out.println("\t\t\u001B[35m└───────────────────┴───────────────────┘\u001B[0m");
                                            System.out.println("\t\t\uD83D\uDC49 0- Ana menü");
                                            System.out.print("\t\tSeçiminiz: ");
                                            String scNextString = scanner.next();
                                            switch (scNextString) {
                                                case "g" -> {
                                                    boolean result = phone.getPhoneContactService().updateContact(getContactInfo(scanner, contacts.get(scNextint - 1)));
                                                    showResult(result, result ? "Kişi güncellendi." : "Kişi güncellenirken bir hata oluştu");
                                                    scanner.nextLine();
                                                }
                                                case "s" -> {
                                                    boolean result = phone.getPhoneContactService().deleteContact(contacts.get(scNextint - 1));
                                                    showResult(result, result ? "Kişi silindi." : "Kişi silinirken bir hata oluştu");
                                                }
                                            }
                                            inputOptException = false;
                                        } catch (InputMismatchException e) {
                                            System.out.println("Hatalı giriş yaptınız.Tekrar giriniz: ");
                                        }
                                    }

                                    inputContactIndexException = false;
                                } catch (InputMismatchException e) {
                                    System.out.println("Hatalı giriş yaptınız.Tekrar giriniz: ");
                                }
                            }
                        }
                    }
                    //endregion

                    case 3 -> {
                        boolean inputContactIndexException = true;
                        while (inputContactIndexException) {
                            scanner.nextLine();
                            try {
                                System.out.print("\tAramak istediğiniz kişinin sıra numarasını giriniz: ");
                                int scContactIndex = scanner.nextInt();
                                phone.getPhoneContactService().call(contacts.get(scContactIndex - 1));
                                System.out.println("\u260e\ufe0f ...");
                                inputContactIndexException = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Hatalı giriş yaptınız.Tekrar giriniz: ");
                            }
                        }
                    }
                }
                inputException = false;
            } catch (InputMismatchException e) {
                System.out.println("Hatalı giriş yaptınız.Tekrar giriniz: ");
            }
        } while (inputException);
    }

    public static void callViaPhoneNumber() {

    }

    private static Contact getContactInfo(Scanner scanner, Contact contact) {
        String name = "";
        String surname = "";
        String phoneNumber = "";
        String email = "";
        String patternPhoneNumber = "\\d{11}";
        String patternEmail = "^(.+)@(\\S+)$";

        boolean wrongNameSurnameInput = true;
        do {
            System.out.print("İsim " + (contact != null ? "(" + contact.getName() + "): " : " :"));
            scanner.nextLine();
            name = scanner.nextLine();
            System.out.print("Soyisim " + (contact != null ? "(" + contact.getSurname() + "): " : " :"));
            surname = scanner.nextLine();
            if (Objects.equals(name, "") && Objects.equals(surname, "")) {
                System.out.println("Lütfen isim yada soyisimden birisini giriniz.");
            } else {
                wrongNameSurnameInput = false;
            }
        } while (wrongNameSurnameInput);

        boolean wrongPhoneNumberInput = true;
        do {
            String phoneNumberInfo = "11 Haneli olmalı";
            if (contact != null && !contact.getPhoneNumber().isEmpty())
                phoneNumberInfo = contact.getPhoneNumber();

            System.out.print("Numara (" + phoneNumberInfo + "):");
            phoneNumber = scanner.nextLine();
            if (contact != null && phoneNumber.isEmpty()) {
                phoneNumber = contact.getPhoneNumber();
            }
            if (Objects.equals(phoneNumber, ""))
                System.out.println("Bir numara girmelisiniz");
            else if (!Pattern.matches(patternPhoneNumber, phoneNumber))
                System.out.println("Hatalı numara girişi yaptınız.");
            else
                wrongPhoneNumberInput = false;
        } while (wrongPhoneNumberInput);

        boolean wrongEmailInput = true;
        do {
            System.out.print("Email " + (contact != null ? "(" + contact.getSurname() + "):" : " :"));
            email = scanner.nextLine();
            if ((contact != null) && email.isEmpty()) {
                email = contact.getEmail();
            }
            if ((!email.isEmpty() | !email.equals("")) && !Pattern.matches(patternEmail, email)) {
                System.out.println("Hatalı email girişi yaptınız");
            } else {
                wrongEmailInput = false;
            }
        } while (wrongEmailInput);


        UUID id = contact != null ? contact.getId() : UUID.randomUUID();
        return new Contact(id, name, surname, phoneNumber, email);
    }

    public static void showInnerMemuStore(Scanner scanner, Phone phone, List<Application> inStoreApplications) {
        boolean inputContactIndexException = true;
        while (inputContactIndexException) {
            scanner.nextLine();
            try {
                System.out.println("\t\u001B[33m┌─────────────────────────────────────────────────────────┐\u001B[0m");
                System.out.println("  \t\uD83D\uDCDD İndirmek istediğiniz uygulama sıra numarasını giriniz:");
                System.out.println("\t\u001B[33m└─────────────────────────────────────────────────────────┘\u001B[0m");
                int scNextint = scanner.nextInt();
                var inStoreApplication = inStoreApplications.get(scNextint - 1);
                var inPhoneApplicationOpt = phone.getApplications().stream().filter(inPhoneApp -> Objects.equals(inPhoneApp.getId(), inStoreApplication.getId())).findFirst();
                boolean result;
                if (inPhoneApplicationOpt.isPresent()) {
                    result = phone.getPhoneAppService().updateApplication(inPhoneApplicationOpt.get());

                    showResult(result, result ? "Uygulama güncellendi." : "Uygulama güncellenirken bir sorun oluştu.");
                } else {
                    result = phone.getPhoneAppService().installApplication(inStoreApplication);
                    showResult(result, result ? "Uygulama yüklendi." : "Uygulama yüklenirken bir sorun oluştu.");
                }

                inputContactIndexException = false;
            } catch (InputMismatchException e) {
                System.out.println("Hatalı giriş yaptınız.Tekrar giriniz: ");
            } catch (PhoneStorageException | ApplicationException e) {
                showFailedMessage(e.getMessage());
            }
        }
    }

    public static void showAllPhonesInSystem() {
        var phoneMap = PhoneManagementSystem.getInstance().listUserPhone();
        phoneMap.forEach((owner, phones) -> {
            System.out.println(owner);
            phones.stream().sorted(Comparator.comparing(Phone::getBrand)).forEach(phone -> System.out.println("\tMarka: " + phone.getBrand() + " Model: " + phone.getModel() + " Seri numarası: " + phone.getSerialNumber()));
        });

    }
}
