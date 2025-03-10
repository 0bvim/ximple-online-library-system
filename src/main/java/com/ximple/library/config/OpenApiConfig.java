package com.ximple.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/*
 * Configuration class for OpenAPI documentation.
 */
public class OpenApiConfig {

  /**
   * Configures the custom OpenAPI documentation for the Online Library API.
   *
   * @return the configured OpenAPI instance
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
                  .title("Online Library API")
                  .version("1.0")
                  .description("Online library system with book search, reservations, and reviews")
                  .termsOfService("https://example.com/terms")
                  .contact(new Contact()
                               .name("API Support")
                               .url("https://example.com/support")
                               .email("niviciusdev@gmail.com"))
                  .license(new License()
                               .name("Apache 2.0")
                               .url("https://www.apache.org/licenses/LICENSE-2.0")))
        .servers(
            List.of(new Server().url("http://localhost:8080/").description("Development server"),
                new Server().url("https://api.example.com").description("Production server")));
  }
}