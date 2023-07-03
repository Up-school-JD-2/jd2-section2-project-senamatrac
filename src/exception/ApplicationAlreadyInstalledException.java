package src.exception;

public class ApplicationAlreadyInstalledException extends ApplicationException {

    public ApplicationAlreadyInstalledException(String applicationName) {
        super(applicationName + " uygulama zaten telefonunuzda yüklü.");
    }
}
