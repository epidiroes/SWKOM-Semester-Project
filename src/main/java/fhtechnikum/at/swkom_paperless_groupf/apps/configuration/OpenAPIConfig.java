package fhtechnikum.at.swkom_paperless_groupf.apps.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Paperless API",
                version = "1.0",
                description = "API documentation for Paperless Application"
        )
)
public class OpenAPIConfig {
}
