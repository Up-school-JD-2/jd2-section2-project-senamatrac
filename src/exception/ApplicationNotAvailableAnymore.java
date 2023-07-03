package src.exception;

public class ApplicationNotAvailableAnymore extends ApplicationException{
    public ApplicationNotAvailableAnymore(String applicationName) {
        super(applicationName + " uygulaması artık Store'da mevcut değil.");
    }
}
