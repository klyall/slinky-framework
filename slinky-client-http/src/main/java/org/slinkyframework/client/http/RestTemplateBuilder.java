package org.slinkyframework.client.http;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateBuilder {

    public static RestTemplateBuilder newRestTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    public RestTemplate build() {

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
//        interceptors.add(new LoggingRequestInterceptor());
//
//        restTemplate.setInterceptors(interceptors);
//
//        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()));

        return restTemplate;
    }
}
