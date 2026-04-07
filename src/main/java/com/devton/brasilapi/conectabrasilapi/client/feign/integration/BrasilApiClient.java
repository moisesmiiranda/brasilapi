package com.devton.brasilapi.conectabrasilapi.client.feign.integration;

import com.devton.brasilapi.conectabrasilapi.client.feign.fallback.BrasilApiClientFallbackFactory;
import com.devton.brasilapi.conectabrasilapi.response.HolidayResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "brasilApiClient", url = "https://brasilapi.com.br/api/feriados/v1", fallbackFactory = BrasilApiClientFallbackFactory.class)
public interface BrasilApiClient {

    @GetMapping("/{ano}")
    List<HolidayResponse> getHolidays(@PathVariable int ano);
}
