package br.com.codegroup.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
public class BusinessError implements Serializable {

    private Integer status;
    private String message;

    public BusinessError(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\n    status : " + status + ",\n    message : " + message + "\n}";
    }
}
