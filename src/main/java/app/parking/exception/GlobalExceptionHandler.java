package app.parking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
		
		ErrorMessage message = ErrorMessage.builder().dateTime(LocalDateTime.now()).message(e.getLocalizedMessage()).build();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
