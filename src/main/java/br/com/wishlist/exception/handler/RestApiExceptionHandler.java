package br.com.wishlist.exception.handler;

import br.com.wishlist.exception.EntityNotFoundException;
import br.com.wishlist.exception.ProductAlreadyExistsException;
import br.com.wishlist.exception.WishlistLimitException;
import br.com.wishlist.model.dto.ErrorMessageDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> argumentTypeException(EntityNotFoundException ex, WebRequest request) {
        log.error(String.valueOf(ex));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> argumentTypeException(ProductAlreadyExistsException ex, WebRequest request) {
        log.error(String.valueOf(ex));
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                 HttpServletRequest request,
                                                                                 BindingResult result) {
        log.error(String.valueOf(ex));

        List<ErrorMessageDTO> errors = new ArrayList<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> {
                String errorMessage = String.format("Field '%s': %s",
                        fieldError.getField(), fieldError.getDefaultMessage());
                errors.add(new ErrorMessageDTO(errorMessage));
            });
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }


    @ExceptionHandler(WishlistLimitException.class)
    public ResponseEntity<ErrorMessageDTO> argumentTypeException(WishlistLimitException ex, WebRequest request) {
        log.error(String.valueOf(ex));
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(ex.getMessage()));
    }
}
