package com.kacpers.cars.feign;

import feign.Client;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
public class CopartConfig {

    private static final Map<String, Collection<String>> headers = Map.ofEntries(
            Map.entry("sec-ch-ua", List.of("\"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")),
            Map.entry("sec-ch-ua-mobile", List.of("?0")),
            Map.entry("sec-ch-ua-platform", List.of("\"Linux\"")),
            Map.entry("upgrade-insecure-requests", List.of("1")),
            Map.entry("user-agent", List.of("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")),
            Map.entry("accept", List.of("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")),
            Map.entry("content-type", List.of("application/json")),
            Map.entry("sec-fetch-site", List.of("same-origin")),
            Map.entry("sec-fetch-mode", List.of("navigate")),
            Map.entry("sec-fetch-user", List.of("?1")),
            Map.entry("sec-fetch-dest", List.of("document")),
            Map.entry("referer", List.of("https://www.copart.ca/")),
            Map.entry("accept-language", List.of("en-US,en;q=0.9")),
            Map.entry("priority", List.of("u=0, i"))
    );

    @Bean
    public RequestInterceptor headersRequestInterceptor() {
        return template -> template.headers(headers);
    }

    @Bean
    public Client feignClient() {
        return new Client.Proxied(null, null, new Proxy(Proxy.Type.HTTP, new InetSocketAddress("104.247.51.75", 8080)));
    }
}
