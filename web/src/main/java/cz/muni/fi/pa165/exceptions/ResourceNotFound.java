package cz.muni.fi.pa165.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="The requested resource was not found.")
public class ResourceNotFound extends RuntimeException {
}