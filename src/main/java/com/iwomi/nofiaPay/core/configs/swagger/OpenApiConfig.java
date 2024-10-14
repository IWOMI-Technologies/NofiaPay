package com.iwomi.nofiaPay.core.configs.swagger;

import com.iwomi.nofiaPay.core.utils.ReadJsonFileUtil;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "iwomi backend team",
                        email = "info@iwomitechnologies.com",
                        url = "https://www.iwomitechnologies.com/iwomitech/en/"
                ),
                description = "OpenApi documentation for NofiaPay project",
                title = "NofiaPay OpenApi specification",
                version = "1.0",
                license = @License(
                        name = "Apache",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8082"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "url to live server"
                )
        }
)
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI baseOpenApi() {
        ReadJsonFileUtil readJsonFileToJsonObject = new ReadJsonFileUtil();

        ApiResponse badRequestApi = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("bad",
                                new Example().value(readJsonFileToJsonObject.read().get("badRequestResponse").toString())))
        ).description("Bad Request!");
        ApiResponse internalServerErrorApi = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("internal",
                                new Example().value(readJsonFileToJsonObject.read().get("internalServerErrorResponse").toString())))
        ).description("Internal server error");
        ApiResponse assetSuccessResponse = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("Method...",
                                new Example().value(readJsonFileToJsonObject.read().get("successResponse").toString())))
        ).description("Successfully registered");
        ApiResponse createdApiResponse = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("created",
                                new Example().value(readJsonFileToJsonObject.read().get("createdResponse").toString())))
        ).description("Successfully registered");

        Components components = new Components();
        components.addResponses("badRequest", badRequestApi);
        components.addResponses("internalServerErrorApi", internalServerErrorApi);
        components.addResponses("successResponse", assetSuccessResponse);
        components.addResponses("createdResponse", createdApiResponse);

        return new OpenAPI().components(components);
    }
}
