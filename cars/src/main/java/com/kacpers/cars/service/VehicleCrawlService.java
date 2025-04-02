package com.kacpers.cars.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kacpers.cars.feign.CopartClient;
import com.kacpers.cars.feign.CopartSession;
import com.kacpers.cars.feign.request.LotSearchRequest;
import com.kacpers.cars.model.LotVehicle;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VehicleCrawlService {

    private final static Integer MAX_RETRIES = 3;

    private final CopartClient copartClient;

    private final CopartSession copartSession;

    private final ObjectMapper objectMapper;

    public String revalidateCopartSession() {
        int retries = 0;
        Response response = copartClient.fetchCookies();
        while (response.status() != 200) {
            response = copartClient.fetchCookies();
            retries++;

            if (retries >= MAX_RETRIES) {
                throw new RuntimeException("Failed to fetch cookies after " + retries + " retries");
            }
        }

        String sessionId = response.headers()
                .get("Set-Cookie")
                .stream()
                .filter(x -> x.startsWith("incap_ses"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No incap_ses cookie found"));

        copartSession.setSessionId(Optional.of(sessionId));
        return sessionId;
    }

    @SneakyThrows
    public Set<LotVehicle> fetchVehicles() {
        String sessionId = copartSession.getSessionId().orElse(revalidateCopartSession());
        LotSearchRequest request = LotSearchRequest.latest();

        int retries = 0;
        ResponseEntity<String> response = copartClient.search(request, sessionId);
        while (!response.getStatusCode().is2xxSuccessful()) {
            response = copartClient.search(request, sessionId);
            retries++;

            if (retries >= MAX_RETRIES) {
                throw new RuntimeException("Failed to fetch lot vehicles after " + retries + " retries");
            }
        }

        // Sneaky json parsing
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode vehiclesNode = rootNode.get("data").get("results").get("content");
        return objectMapper.readValue(vehiclesNode.toString(), new TypeReference<>() {});
    }
}
