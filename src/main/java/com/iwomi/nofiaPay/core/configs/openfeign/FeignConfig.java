package com.iwomi.nofiaPay.core.configs.openfeign;

import feign.Feign;
import feign.Logger;
import feign.slf4j.Slf4jLogger;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


@Configuration
public class FeignConfig {

    // Method to configure OkHttpClient with a custom SSL setup
    @Bean
    public OkHttpClient okHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustManager[] trustAll = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
                        return new java.security.cert.X509Certificate[]{};
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAll, new java.security.SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAll[0])
                .build();
    }

    // Method to configure Feign Builder
    @Bean
    public Feign.Builder feignBuilder() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return Feign.builder()
                .client(new feign.okhttp.OkHttpClient(okHttpClient())) // Ensure the full class name is used here
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL);
    }
}
