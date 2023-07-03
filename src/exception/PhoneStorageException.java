package src.exception;

public class PhoneStorageException extends Exception{
    public PhoneStorageException() {
        super("Telefonun belleği dolu.");
    }
}
