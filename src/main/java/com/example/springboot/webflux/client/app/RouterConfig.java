package com.example.springboot.webflux.client.app;

import com.example.springboot.webflux.client.app.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductHandler handler) {
        return route(GET("/api/client"), handler::toList)
                .andRoute(GET("/api/client/{id}"), handler::look)
                .andRoute(GET("/api/client"), handler::save)
                .andRoute(GET("/api/client/{id}"), handler::update)
                .andRoute(GET("/api/client/{id}"), handler::delete)
                .andRoute(GET("/api/client/upload/{id}"), handler::upload);
    }
}
