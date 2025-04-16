package com.kacpers.cars.feign;

import feign.Client;
import feign.Request;
import feign.Response;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

@RequiredArgsConstructor
class DynamicProxyClient implements Client {

    private final ProxySelector proxySelector;

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        proxySelector.selectProxy(new InetSocketAddress("79.76.105.113", 3128));
        InetSocketAddress proxyAddress = proxySelector.getSelectedProxy();
        Client client = new Client.Proxied(null, null, new Proxy(Proxy.Type.HTTP, proxyAddress));
        return client.execute(request, options);
    }
}
