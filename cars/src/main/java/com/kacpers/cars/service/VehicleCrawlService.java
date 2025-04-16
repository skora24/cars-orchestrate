package com.kacpers.cars.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kacpers.cars.feign.CopartClient;
import com.kacpers.cars.feign.request.LotSearchRequest;
import com.kacpers.cars.feign.response.LotDetailsResponse;
import com.kacpers.cars.feign.response.LotSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleCrawlService {

    private final static Integer MAX_RETRIES = 3;

    private Optional<String> copartSession = Optional.empty();

    private final CopartClient copartClient;

    private final ObjectMapper objectMapper;

    public synchronized String getCopartSession() {
        if (copartSession.isPresent()) return copartSession.get();

        ChromeOptions options = new ChromeOptions();

        options.setProxy(new Proxy().setHttpProxy("79.76.105.113:3128"));
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.copart.ca");

        String sessionId = driver.manage()
            .getCookies()
            .stream()
            .filter(cookie -> cookie.getName().startsWith("incap_ses"))
            .map(cookie -> String.format("%s=%s;", cookie.getName(), cookie.getValue()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Incap session not found"));

        driver.quit();
        copartSession = Optional.of(sessionId);
        return sessionId;
    }

    @SneakyThrows
    public LotSearchResponse fetchVehicles(LotSearchRequest request) {
        String sessionId = getCopartSession();

        int retries = 0;
        ResponseEntity<String> response = null;

        try {
            response = copartClient.search(request, sessionId);
        } catch (Exception ignored) {
        }

        while (response == null || !response.getStatusCode().is2xxSuccessful()) {
            try {
                response = copartClient.search(request, sessionId);
            } catch (Exception ignored) {}
            retries++;

            if (retries >= MAX_RETRIES) {
                throw new RuntimeException("Failed to fetch lot vehicles after " + retries + " retries");
            }
        }

        // Sneaky json parsing
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode vehiclesNode = rootNode.get("data").get("results");
        return objectMapper.readValue(vehiclesNode.toString(), new TypeReference<>() {});
    }

    public LotDetailsResponse fetchVehicleDetails(Long lotNumber) {
        String sessionId = getCopartSession();

        int retries = 0;
        ResponseEntity<LotDetailsResponse> response = null;

        try {
            response = copartClient.details(lotNumber, sessionId);
        } catch (Exception ignored) {
        }

        while (response == null || !response.getStatusCode().is2xxSuccessful()) {
            try {
                response = copartClient.details(lotNumber, sessionId);
            } catch (Exception ignored) {}
            retries++;

            if (retries >= MAX_RETRIES) {
                throw new RuntimeException("Failed to fetch lot vehicle details after " + retries + " retries");
            }
        }

        return response.getBody();
    }
}
