package br.com.codegroup.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ArgumentsErrorResponseDTO implements Serializable {

    private List<String> errors = new ArrayList<>();

}
