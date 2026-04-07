package com.devton.brasilapi.conectabrasilapi.controller;

import com.devton.brasilapi.conectabrasilapi.client.feign.integration.BrasilApiClient;
import com.devton.brasilapi.conectabrasilapi.response.HolidayResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DisplayName("HolidayController - Integration Tests")
class HolidayControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BrasilApiClient brasilApiClient;

    @Test
    @DisplayName("GET /holidays/{ano} deve retornar lista de feriados com status 200")
    void getHolidays_shouldReturnListOfHolidaysWithStatus200() throws Exception {
        int year = 2026;
        List<HolidayResponse> mockHolidays = List.of(
                new HolidayResponse("2026-01-01", "Confraternização Universal", "national"),
                new HolidayResponse("2026-04-21", "Tiradentes", "national"),
                new HolidayResponse("2026-09-07", "Independência do Brasil", "national")
        );

        when(brasilApiClient.getHolidays(year)).thenReturn(mockHolidays);

        mockMvc.perform(get("/holidays/{ano}", year))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].date").value("2026-01-01"))
                .andExpect(jsonPath("$[0].name").value("Confraternização Universal"))
                .andExpect(jsonPath("$[0].type").value("national"))
                .andExpect(jsonPath("$[1].date").value("2026-04-21"))
                .andExpect(jsonPath("$[1].name").value("Tiradentes"))
                .andExpect(jsonPath("$[1].type").value("national"))
                .andExpect(jsonPath("$[2].date").value("2026-09-07"))
                .andExpect(jsonPath("$[2].name").value("Independência do Brasil"))
                .andExpect(jsonPath("$[2].type").value("national"));

        verify(brasilApiClient, times(1)).getHolidays(year);
    }

    @Test
    @DisplayName("GET /holidays/{ano} deve retornar lista vazia com status 200 quando não há feriados")
    void getHolidays_shouldReturnEmptyListWithStatus200() throws Exception {
        int year = 1800;

        when(brasilApiClient.getHolidays(year)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/holidays/{ano}", year))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(brasilApiClient, times(1)).getHolidays(year);
    }

    @Test
    @DisplayName("GET /holidays/{ano} deve repassar o ano correto ao BrasilApiClient")
    void getHolidays_shouldDelegateCorrectYearToClient() throws Exception {
        int year = 2025;

        when(brasilApiClient.getHolidays(year)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/holidays/{ano}", year))
                .andExpect(status().isOk());

        verify(brasilApiClient, times(1)).getHolidays(year);
        verify(brasilApiClient, never()).getHolidays(intThat(y -> y != year));
    }

    @Test
    @DisplayName("GET /holidays/{ano} deve propagar exceção quando o client externo falha")
    void getHolidays_shouldPropagateExceptionWhenClientFails() {
        int year = 2026;

        when(brasilApiClient.getHolidays(year)).thenThrow(new RuntimeException("BrasilAPI indisponível"));

        assertThrows(Exception.class, () ->
                mockMvc.perform(get("/holidays/{ano}", year))
        );

        verify(brasilApiClient, times(1)).getHolidays(year);
    }

    @Test
    @DisplayName("GET /holidays/{ano} deve retornar feriado único corretamente")
    void getHolidays_shouldReturnSingleHolidayCorrectly() throws Exception {
        int year = 2026;
        List<HolidayResponse> singleHoliday = List.of(
                new HolidayResponse("2026-12-25", "Natal", "national")
        );

        when(brasilApiClient.getHolidays(year)).thenReturn(singleHoliday);

        mockMvc.perform(get("/holidays/{ano}", year))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].date").value("2026-12-25"))
                .andExpect(jsonPath("$[0].name").value("Natal"))
                .andExpect(jsonPath("$[0].type").value("national"));
    }
}





