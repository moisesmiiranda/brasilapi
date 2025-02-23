package com.devton.brasilapi.conectabrasilapi.controller;

import com.devton.brasilapi.conectabrasilapi.client.BrasilApiClientHolidays;
import com.devton.brasilapi.conectabrasilapi.response.HolidayResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/holidays")
@Tag(name = "Holidays", description = "Consulta feriados nacionais através da BrasilAPI")
public class HolidayController {
    private final BrasilApiClientHolidays brasilApiClientHolidays;

    public HolidayController(BrasilApiClientHolidays brasilApiClientHolidays) {
        this.brasilApiClientHolidays = brasilApiClientHolidays;
    }

    @GetMapping("/{ano}")
    @Operation(summary = "Buscar feriados por ano", description = "Retorna a lista de feriados nacionais para o ano informado.")
    public List<HolidayResponse> getHolidays(@PathVariable int ano) {
        return brasilApiClientHolidays.searchHolidays(ano);
    }
}
