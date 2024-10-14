package com.iwomi.nofiaPay.core.utils;

import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJsonFileUtil {
    /** Convert json file to JsonObject*/
    public JSONObject read() {
        // Use ClassPathResource to load the JSON file from the resources directory
        ClassPathResource resource = new ClassPathResource("openapi/response.json");
        String content = null;

        try (InputStream inputStream = resource.getInputStream()) {
            // Read the InputStream and convert it to a String
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle the exception or log it appropriately
        }

        return new JSONObject(content);
    }
}
