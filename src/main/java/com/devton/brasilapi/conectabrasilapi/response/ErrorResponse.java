package com.devton.brasilapi.conectabrasilapi.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        String message,
        LocalDateTime timestamp
) {}

