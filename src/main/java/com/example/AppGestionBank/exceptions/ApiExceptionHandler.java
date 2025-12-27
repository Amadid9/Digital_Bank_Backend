package com.example.AppGestionBank.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<?> handleClientNotFound(ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "CLIENT_NOT_FOUND",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(CompteBancaireNotFoundException.class)
    public ResponseEntity<?> handleCompteNotFound(CompteBancaireNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "COMPTE_NOT_FOUND",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(MontantNotSufficientException.class)
    public ResponseEntity<?> handleSoldeInsuffisant(MontantNotSufficientException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "SOLDE_INSUFFISANT",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobal(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "INTERNAL_ERROR",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(CompteSuspendedException.class)
    public ResponseEntity<?> handleSuspended(CompteSuspendedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "COMPTE_SUSPENDED",
                        "message", ex.getMessage()
                )
        );
    }

}

