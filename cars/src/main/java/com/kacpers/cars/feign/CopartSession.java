package com.kacpers.cars.feign;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Getter
@Setter
public class CopartSession {
    private Optional<String> sessionId = Optional.empty();
}
