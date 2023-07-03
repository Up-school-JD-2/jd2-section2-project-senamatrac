package src.exception;

public class ApplicationCannotUninstall extends ApplicationException{
    public ApplicationCannotUninstall(String applicationName) {
        super(applicationName + " uygulaması önceden yüklü bir uygulamadır; kaldırılamaz.");
    }
}
