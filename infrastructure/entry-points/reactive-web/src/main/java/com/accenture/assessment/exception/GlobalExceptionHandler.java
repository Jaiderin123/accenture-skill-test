package com.accenture.assessment.exception;

import com.accenture.assessment.dto.ApiResponse;
import com.accenture.assessment.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Order(-2)
@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler{

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties webProperties,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        this.setMessageReaders(serverCodecConfigurer.getReaders());
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }


    /*public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties.Resources resources,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageReaders(serverCodecConfigurer.getReaders());
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }*/

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::customErrorResponse);
    }

    private Mono<ServerResponse> customErrorResponse(ServerRequest request) {
        Throwable error = getError(request);
        log.error(error.getMessage(), error);

        HttpStatus status = resolveHttpStatus(error);

        Object data = null;
        String message = error != null ? error.getMessage() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

        if (error instanceof ServerRequestValidationException srve)
            data = !srve.getErrors().isEmpty() ? srve.getErrors() : null;

        if (error instanceof ServerWebInputException swie) {
            message = "Data request format invalid";
            data = swie.getCause().getLocalizedMessage();
        }
        return ServerResponse.status(status)
                .contentType(APPLICATION_JSON)
                .bodyValue(buildResponse(data, status.value(), message));
    }

    private HttpStatus resolveHttpStatus(Throwable error) {
        return switch (error) {
            case BusinessException be -> HttpStatus.BAD_REQUEST;
            case ServerRequestValidationException sve -> HttpStatus.BAD_REQUEST;
            case ServerWebInputException swie -> HttpStatus.BAD_REQUEST;
            case null, default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private <T> ApiResponse<T> buildResponse(T data , int status, String message){
        return new ApiResponse<>(data, status, message);
    }
}
