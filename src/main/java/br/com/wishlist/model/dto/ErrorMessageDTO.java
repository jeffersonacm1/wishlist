package br.com.wishlist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 623624256L;

    private String message;

}
