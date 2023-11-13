package br.com.codegroup.domain.exceptions;

public class ProjectLockedException extends RuntimeException {

    public ProjectLockedException(String message) {
        super(message);
    }
}
