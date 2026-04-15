package com.accenture.assessment.franchise;

import com.accenture.assessment.config.ApiPathBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
public class FranchiseRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> franchiseRoutes(FranchiseHandler handlerRoutes, ApiPathBuilder apiPathBuilder){
        String basePath = apiPathBuilder.buildPath("/franchise");
        log.info("Franchise base Path APP -> {}", basePath);
        return nest(
                path(basePath),
                route(
                        POST("/save"),
                        handlerRoutes::listenPOSTSave
                ).andRoute(
                        PUT("/update-name"),
                        handlerRoutes::listenPUTUpdateName
                ).andRoute(
                        GET("/top-stock-products"),
                        handlerRoutes::listenGETTopStockProducts
                )
        );
    }
}
