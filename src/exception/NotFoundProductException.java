package exception;

public class NotFoundProductException extends Exception {
    public NotFoundProductException(String message) {
        super(message);
    }

    public NotFoundProductException() {
        super("ID điện thoại không tồn tại.");
    }
}
