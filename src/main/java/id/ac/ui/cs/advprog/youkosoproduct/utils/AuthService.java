package id.ac.ui.cs.advprog.youkosoproduct.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.youkosoproduct.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.Generated;

@Service
@Generated
public class AuthService {
    @Value("${auth.url}")
    private String authUrl;

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthService(RestTemplate restTemplate, ExecutorService executorService, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.executorService = executorService;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<AuthResponse> validateToken(String authHeader) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String token = extractToken(authHeader);
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<String> response = restTemplate.exchange(
                        authUrl,
                        org.springframework.http.HttpMethod.GET,
                        entity,
                        String.class);
                if (response.getStatusCode() != HttpStatus.OK) {
                    throw new UnauthorizedException("Invalid token");
                }
                return objectMapper.readValue(response.getBody(), AuthResponse.class);
            } catch (Exception e) {
                throw new UnauthorizedException("An error occurred during authentication");
            }
        }, executorService);
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new UnauthorizedException("Missing or invalid Authorization header");
    }
}
