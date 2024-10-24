package com.iwomi.nofiaPay.frameworks.externals.iwomipay.domain;

import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.errors.exceptions.UnAuthorizedException;
import com.iwomi.nofiaPay.core.utils.CoreUtils;
import com.iwomi.nofiaPay.frameworks.externals.clients.IwomiPayClient;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.DigitalPaymentDto;
import com.iwomi.nofiaPay.frameworks.externals.iwomipay.dto.IwomiAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class Payment implements IPayment {
    private final IwomiPayClient client;

    @Value("${externals.iwomi.username}")
    private String username;

    @Value("${externals.iwomi.password}")
    private String password;

    @Value("${externals.iwomi.apiKeys.momo}")
    private String momoKey;

    @Value("${externals.iwomi.apiSecrets.momo}")
    private String momoSecret;

    @Value("${externals.iwomi.apiKeys.om}")
    private String omKey;

    @Value("${externals.iwomi.apiSecrets.om}")
    private String omSecret;

    @Override
    public String auth(IwomiAuthDto dto) {
        ResponseEntity<?> response = client.authenticate(dto);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody() instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<String, Object> data = (Map<String, Object>) response.getBody();

                if ("01".equalsIgnoreCase((String) data.get("status"))) return data.get("token").toString();

                else throw new UnAuthorizedException("Error getting the token for payment initialisation.");

            } else throw new GeneralException("Server error occurred. Please contact admin.");
        }
        throw new GeneralException("Response body is not in the expected format.");
    }

    @Override
    public Map<String, Object> pay(DigitalPaymentDto dto) {
        IwomiAuthDto authDto = new IwomiAuthDto(username, password);
        String token = "Bearer "+ auth(authDto);
//        System.out.println("Token ----- "+token);

        String key = Objects.equals(dto.type(), "om") ? omKey : momoKey;
        String secret = Objects.equals(dto.type(), "om") ? omSecret : momoSecret;
        String joined = key + ":" + secret;

        String encoded = Base64.getEncoder().encodeToString(joined.getBytes());

        var object = CoreUtils.objectToMap(dto);

        return client.iwomiPay(token, encoded, object);
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<Map<String, Object>> checkPaymentStatusWithBackoff(String internalId) {
        // Define configuration parameters locally
        long delayPeriodSeconds = 10; // Total retry duration in seconds (3 minutes) = 180 sec
        long maxInitialDelaySeconds = 1;

        Map<String, Object> checking = null;
        int retryCount = 0;
        long initialDelayMillis = TimeUnit.SECONDS.toMillis(maxInitialDelaySeconds); // Initial delay in milliseconds
        long maxDelaySeconds = 64; // Maximum delay in seconds
        long totalDurationMillis = TimeUnit.SECONDS.toMillis(delayPeriodSeconds); // Total duration in milliseconds
        long startTime = System.currentTimeMillis(); // Record start time

        // iwomi pay auth
        final String token = "Bearer "+ auth(new IwomiAuthDto(username, password));
        while (true) {
            checking = client.paymentStatus(token, internalId); // Make API call

            if (isPaymentCompleted(checking)) {
                System.out.println("Payment completed.");
                return CompletableFuture.completedFuture(checking);
            }

            // Calculate elapsed time
            long elapsedTimeMillis = System.currentTimeMillis() - startTime;
            if (elapsedTimeMillis >= totalDurationMillis) {
                System.out.println("Configured time elapsed, returning status.");
                return CompletableFuture.completedFuture(checking);
            }

            // Exponential backoff with jitter
            long delaySeconds = Math.min(maxInitialDelaySeconds * (1L << retryCount), maxDelaySeconds); // Calculate delay in seconds
            long delayMillis = TimeUnit.SECONDS.toMillis(delaySeconds);
            delayMillis += new Random().nextInt(1000); // Adding some jitter

            System.out.println("Retrying in " + delaySeconds + " seconds");
            retryCount++;
            try {
                TimeUnit.MILLISECONDS.sleep(delayMillis); // Convert delay to milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return CompletableFuture.failedFuture(new RuntimeException("Interrupted while waiting for payment status", e));
            }
        }
    }

    private boolean isPaymentCompleted(Map<String, Object> response) {
        // Implement this method based on your application's logic
        boolean state = response != null && "01".equalsIgnoreCase(response.get("status").toString());
        System.out.println("Payment completed status: " + state);
        return state;
    }

}
