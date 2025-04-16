package com.kacpers.cars.feign;

import feign.Client;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CopartConfig {

    private static final Map<String, Collection<String>> headers = Map.ofEntries(
//        Map.entry("sec-ch-ua", List.of("\"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")),
//        Map.entry("sec-ch-ua-mobile", List.of("?0")),
//        Map.entry("sec-ch-ua-platform", List.of("\"Linux\"")),
//        Map.entry("upgrade-insecure-requests", List.of("1")),
//        Map.entry("user-agent", List.of("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")),
//        Map.entry("accept", List.of("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")),
//        Map.entry("content-type", List.of("application/json")),
//        Map.entry("sec-fetch-site", List.of("same-origin")),
//        Map.entry("sec-fetch-mode", List.of("navigate")),
//        Map.entry("sec-fetch-user", List.of("?1")),
//        Map.entry("sec-fetch-dest", List.of("document")),
//        Map.entry("referer", List.of("https://www.copart.ca/")),
//        Map.entry("accept-language", List.of("en-US,en;q=0.9")),
//        Map.entry("priority", List.of("u=0, i"))
            Map.entry("Accept", List.of("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")),
            Map.entry("Accept-Encoding", List.of("gzip, deflate, br, zstd")),
            Map.entry("Accept-Language", List.of("en-US,en;q=0.9")),
            Map.entry("Priority", List.of("u=0, i")),
            Map.entry("sec-ch-ua", List.of("\"Not:A-Brand\";v=\"24\", \"Chromium\";v=\"134\"")),
            Map.entry("sec-ch-ua-mobile", List.of("?0")),
            Map.entry("sec-ch-ua-platform", List.of("\"Linux\"")),
            Map.entry("sec-fetch-dest", List.of("document")),
            Map.entry("sec-fetch-mode", List.of("navigate")),
            Map.entry("sec-fetch-site", List.of("none")),
            Map.entry("sec-fetch-user", List.of("?1")),
            Map.entry("upgrade-insecure-requests", List.of("1")),
            Map.entry("user-agent", List.of("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36"))

    );

    private final ProxySelector proxySelector;

    @Bean
    public RequestInterceptor headersRequestInterceptor() {
        return template -> template.headers(headers);
    }

    @Bean
    public Client feignClientCopart() {
        return new DynamicProxyClient(proxySelector);
    }
}
