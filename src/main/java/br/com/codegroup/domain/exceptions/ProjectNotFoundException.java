package br.com.codegroup.domain.exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(String mensagem) {
        super(mensagem);
    }
}
