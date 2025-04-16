package com.kacpers.cars.feign;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CopartFetchInfo {
    private Integer lastPage = -1;

    public synchronized void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }
}
