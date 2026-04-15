package com.accenture.assessment.product;

import com.accenture.assessment.config.ApiPathBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
public class ProductRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> productRoutes(ProductHandler handlerRoutes, ApiPathBuilder apiPathBuilder){
        String basePath = apiPathBuilder.buildPath("/product");
        log.info("Product base Path APP -> {}", basePath);
        return nest(
                path(basePath),
                route(
                        POST("/save"),
                        handlerRoutes::listenPOSTSave
                ).andRoute(
                        PUT("/update-name"),
                        handlerRoutes::listenPUTUpdateName
                ).andRoute(
                        PUT("/update-stock"),
                        handlerRoutes::listenPUTUpdateStock
                ).andRoute(
                        DELETE("/delete"),
                        handlerRoutes::listenDELETEProduct
                )
        );
    }
}
