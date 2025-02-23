package com.devton.brasilapi.conectabrasilapi.client;
import com.devton.brasilapi.conectabrasilapi.response.HolidayResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class BrasilApiClientHolidays {
    private final WebClient webClient;

    public BrasilApiClientHolidays(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://brasilapi.com.br/api/feriados/v1/")
                .build();
    }

    public List<HolidayResponse> searchHolidays(int ano) {
        return webClient.get()
                .uri("/{ano}", ano)
                .retrieve()
                .bodyToFlux(HolidayResponse.class)
                .collectList()
                .block();
    }
}
