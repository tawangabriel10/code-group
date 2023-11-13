package br.com.codegroup.domain.exceptions;

public class PersonNotEmployeeException  extends RuntimeException {

    public PersonNotEmployeeException(String message) {
        super(message);
    }
}
