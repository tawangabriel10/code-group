package br.com.codegroup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final String APP_NAME = "Code Group Test";

    private static final String APP_VERSION = "1.0.0";

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(APP_NAME)
                        .description("Aplicação de Gerenciamento de Projetos ")
                        .version(APP_VERSION)
                        .description(""));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api-v1")
                .packagesToScan("br.com.codegroup.controller")
                .pathsToExclude("/pessoas/**", "/profile/**", "/projetoes/**")
                .build();
    }
}
