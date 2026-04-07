package com.devton.brasilapi.conectabrasilapi.exception;
import com.devton.brasilapi.conectabrasilapi.response.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExternalApiException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleExternalApiException(ExternalApiException ex) {
        return new ErrorResponse("EXTERNAL_API_UNAVAILABLE", ex.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponse handleFeignException(FeignException ex) {
        return new ErrorResponse("EXTERNAL_API_ERROR", "External API call failed: " + ex.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex) {
        return new ErrorResponse("INTERNAL_ERROR", "Error processing request", LocalDateTime.now());
    }
}
