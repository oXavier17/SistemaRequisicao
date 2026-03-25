package com.example.Sistema_Requisicao.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.SQLException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        String message = ex.getMessage();
        
        // Se o erro vier do SQL (RAISERROR), vamos limpar a mensagem
        if (ex.getCause() instanceof SQLException || message.contains("java.sql.SQLException")) {
            // Tenta pegar a mensagem limpa do RAISERROR
            String cleanMessage = extractSqlMessage(message);
            return ResponseEntity.badRequest().body(new ErrorResponse("Erro de Banco de Dados", cleanMessage));
        }

        return ResponseEntity.internalServerError().body(new ErrorResponse("Erro Interno", "Ocorreu um erro inesperado."));
    }

    private String extractSqlMessage(String fullMessage) {
        // As mensagens do SQL costumam vir após o texto "Default message:" ou entre colchetes
        // Aqui limpamos os prefixos chatos do Hibernate
        if (fullMessage.contains("]")) {
            return fullMessage.substring(fullMessage.lastIndexOf("]") + 1).trim();
        }
        return fullMessage;
    }
    
    // Classe interna simples para o JSON de erro ficar bonito
    public record ErrorResponse(String titulo, String mensagem) {}
}