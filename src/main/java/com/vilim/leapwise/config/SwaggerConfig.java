package com.vilim.leapwise.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @SuppressWarnings("checkstyle:LineLength")
    private static final String API_DESCRIPTION = """
            REST API for creating and evaluating logical expressions
            \s
            STEP 1 - Enter a logical expression containing logical and relational operations. The expression only accepts data fields of the customer model and currently ignores brackets.
            \s
            STEP 2 - Enter a JSON object representing a customer together with the ID of a created expression. Fill in the fields with data that corresponds to the types and structure provided in the example.
            \s
            RESULT - The app returns 'true' or 'false' if all inputs and their rules are satisfied OR throw an exception.
            \s""";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Expression Evaluator API")
                        .description(API_DESCRIPTION)
                        .version("v1.0"));
    }
}
