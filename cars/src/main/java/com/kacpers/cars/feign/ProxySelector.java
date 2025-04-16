package com.kacpers.cars.feign;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ProxySelector {
    private final List<InetSocketAddress> proxies = new CopyOnWriteArrayList<>();

    @Getter
    private InetSocketAddress selectedProxy = null;

    public void addProxy(InetSocketAddress proxy) {
        proxies.add(proxy);
    }

    public void removeProxy(InetSocketAddress proxy) {
        proxies.remove(proxy);
    }

    public synchronized void selectProxy(InetSocketAddress proxy) {
        selectedProxy = proxy;
    }

    public InetSocketAddress getRandomProxy() {
        if (proxies.isEmpty()) {
            return null;
        }
        return proxies.get(ThreadLocalRandom.current().nextInt(proxies.size()));
    }
}