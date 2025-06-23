package app.parking.exception;

public class NoVacantSpaceException extends RuntimeException {
	
	public NoVacantSpaceException(String message) {
		super(message);
	}
}
