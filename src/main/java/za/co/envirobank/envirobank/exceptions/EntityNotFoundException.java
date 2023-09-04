package za.co.envirobank.envirobank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private UUID fieldValue;
    public EntityNotFoundException(String resourceName,String fieldName,UUID fieldValue) {
        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
