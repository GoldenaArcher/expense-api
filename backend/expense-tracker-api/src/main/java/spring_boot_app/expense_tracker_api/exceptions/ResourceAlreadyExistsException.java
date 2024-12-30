package spring_boot_app.expense_tracker_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
