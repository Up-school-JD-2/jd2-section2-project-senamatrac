package src.exception;

public class ApplicationAlreadyUpdated extends ApplicationException {
    public ApplicationAlreadyUpdated(String applicationName) {
        super(applicationName +" uygulamasının güncel versiyonu telefonunuzda yüklü.");
    }
}
