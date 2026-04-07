package com.devton.brasilapi.conectabrasilapi.service;
import com.devton.brasilapi.conectabrasilapi.client.feign.integration.BrasilApiClient;
import com.devton.brasilapi.conectabrasilapi.response.HolidayResponse;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class HolidayService {
    private final BrasilApiClient brasilApiClient;
    public HolidayService(BrasilApiClient brasilApiClient) {
        this.brasilApiClient = brasilApiClient;
    }
    public List<HolidayResponse> getHolidaysByYear(int year) {
        return brasilApiClient.getHolidays(year);
    }
}
