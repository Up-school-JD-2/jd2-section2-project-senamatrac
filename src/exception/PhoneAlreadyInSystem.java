package src.exception;

public class PhoneAlreadyInSystem extends Exception {
    public PhoneAlreadyInSystem(String serialNumber) {
        super(serialNumber + " bu seri numaralı telefon sisteme kayıtlı .");
    }
}
