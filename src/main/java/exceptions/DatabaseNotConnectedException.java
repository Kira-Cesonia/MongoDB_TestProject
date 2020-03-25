package exceptions;

public class DatabaseNotConnectedException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatabaseNotConnectedException(String errorMessage) {
        super(errorMessage);
    }
}
