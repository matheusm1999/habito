package matheus.dev.habito.handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException exception) {
        final Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation failed");

        final Map<String, List<String>> fieldErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                fieldError ->
                        fieldErrors
                                .computeIfAbsent(
                                        fieldError.getField(), key -> new java.util.ArrayList<>())
                                .add(fieldError.getDefaultMessage()));
        body.put("fields", fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }
}
