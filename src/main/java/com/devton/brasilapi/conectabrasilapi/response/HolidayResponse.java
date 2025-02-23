package com.devton.brasilapi.conectabrasilapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HolidayResponse (
    @JsonProperty("date") String data,
    @JsonProperty("name") String nome,
    @JsonProperty("type") String tipo
){}
