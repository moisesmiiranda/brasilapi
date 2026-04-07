package com.devton.brasilapi.conectabrasilapi.client.feign.fallback;
import com.devton.brasilapi.conectabrasilapi.client.feign.integration.BrasilApiClient;
import com.devton.brasilapi.conectabrasilapi.exception.ExternalApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
@Component
public class BrasilApiClientFallbackFactory implements FallbackFactory<BrasilApiClient> {
    private static final Logger log = LoggerFactory.getLogger(BrasilApiClientFallbackFactory.class);
    @Override
    public BrasilApiClient create(Throwable cause) {
        return ano -> {
            log.error("BrasilAPI unavailable for year {}: {}", ano, cause.getMessage());
            throw new ExternalApiException("BrasilAPI is currently unavailable. Please try again later.", cause);
        };
    }
}
