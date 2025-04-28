package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProxyController {

    private final RestTemplate restTemplate;

    public ProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ResponseEntity<String> proxyToAppian() {
        // Construct the URL to forward the request to
        String url = "https://gcperr.appian-sites.net/suite/webapi/retrieve";

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic YWRtaW4udXNlcjppbmVlZHRvYWRtaW5pc3Rlcg==");

        // Create the request entity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Forward the request to Appian with the Authorization header and return the response
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}
