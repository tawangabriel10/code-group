package br.com.codegroup.interceptor;

import br.com.codegroup.domain.dto.BusinessError;
import br.com.codegroup.domain.exceptions.PersonNotEmployeeException;
import br.com.codegroup.domain.exceptions.PersonNotFoundException;
import br.com.codegroup.domain.exceptions.ProjectLockedException;
import br.com.codegroup.domain.exceptions.ProjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InterceptorHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<BusinessError> personNotFound(PersonNotFoundException e, HttpServletRequest request) {
        BusinessError error = new BusinessError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<BusinessError> projectNotFound(ProjectNotFoundException e, HttpServletRequest request) {
        BusinessError error = new BusinessError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PersonNotEmployeeException.class)
    public ResponseEntity<BusinessError> personNotEmployee(PersonNotEmployeeException e, HttpServletRequest request) {
        BusinessError error = new BusinessError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ProjectLockedException.class)
    public ResponseEntity<BusinessError> projectLocked(ProjectLockedException e, HttpServletRequest request) {
        BusinessError error = new BusinessError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
