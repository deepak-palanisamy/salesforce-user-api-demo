package net.breezeware.propel.demo.salesforceuserapidemo.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesforceService {

    // Retrieve all users
    public Object getUsers() {
        log.info("Entering getUsers()");

        RestTemplate restTemplate = new RestTemplate();

        String soqlQuery = "SELECT Id, Name FROM User";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String bearerToken = getToken();
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken));
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<Object> response = restTemplate.exchange(
                    new URI("https://breezeware3-dev-ed.develop.my.salesforce.com/services/data/v59.0/query?q="
                            + URLEncoder.encode(soqlQuery, StandardCharsets.UTF_8)),
                    HttpMethod.GET, request, Object.class);
            return response.getBody();
        } catch (URISyntaxException e) {
            log.error("Error while getting users", e);
            log.info("Leaving getUsers()");
        }

        log.info("Leaving getUsers()");
        return null;
    }

    private String getToken() {
        log.info("Entering getToken()");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_secret", "8BA80D435F42FA23F8A0FECD1A44AA83811ABDC3552FDBA2F878471E82299D65");
        params.add("client_id",
                "3MVG9pRzvMkjMb6m6dJx0Ld_MNOOt90M_iF9WreeyPDb5YKJPIyM1_8uqSjvwxKDxArJc7O_aZVCZfwKgdsqu");
        params.add("grant_type", "client_credentials");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = null;
        try {
            response = restTemplate.postForEntity(
                    new URI("https://breezeware3-dev-ed.develop.my.salesforce.com/services/oauth2/token"), request,
                    Map.class);
        } catch (URISyntaxException e) {
            log.error("Error while getting token", e);
            log.info("Leaving getToken()");
        }

        Map<String, String> responseBody = response.getBody();
        assert responseBody != null;
        String accessToken = responseBody.get("access_token");

        log.info("Leaving getToken()");
        return accessToken;
    }
}
