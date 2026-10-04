package dev.ahmed.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
            title = "OpenApi specification - Ahmed",
            description = "Open api description for Blog application backend",
            version = "1.0",
            license = @License(
                    name = ("MIT License"),
                    url = "https://github.com/Ahmed-AF-I/blog-application/blob/master/LICENSE"
            )
    ),

    servers = {
            @Server(
                    description = "starting the server",
                    url = "http://localhost:8080"
            )
    }
)
public class OpenApiConfig {
}
