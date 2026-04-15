package com.accenture.assessment.product;

import com.accenture.assessment.dto.ApiResponse;
import com.accenture.assessment.exception.ServerRequestValidationException;
import com.accenture.assessment.product.dto.CreateProductDTO;
import com.accenture.assessment.product.dto.UpdateProductNameDTO;
import com.accenture.assessment.product.dto.UpdateProductStockDTO;
import com.accenture.assessment.product.usecase.CreateProductUseCase;
import com.accenture.assessment.product.usecase.DeleteProductUseCase;
import com.accenture.assessment.product.usecase.UpdateProductNameUseCase;
import com.accenture.assessment.product.usecase.UpdateProductStockUseCase;
import com.accenture.assessment.validator.ServerRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class ProductHandler {
    private final ServerRequestValidator serverRequestValidator;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductNameUseCase updateProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public Mono<ServerResponse> listenPOSTSave(ServerRequest serverRequest){
        return serverRequest.bodyToMono(CreateProductDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Product info is required")))
                .flatMap(serverRequestValidator::validate)
                .flatMap(createProductDTO ->
                        createProductUseCase.create(
                                createProductDTO.branchId(),
                                createProductDTO.name(),
                                createProductDTO.stock()
                        )
                )
                .flatMap(responseData ->
                        ServerResponse.status(201)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,201,"Product created successfully"))
                );
    }

    public Mono<ServerResponse> listenPUTUpdateName(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UpdateProductNameDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Product info is required")))
                .flatMap(serverRequestValidator::validate)
                .flatMap(updateProductNameDTO ->
                        updateProductUseCase.updateName(
                                updateProductNameDTO.id(),
                                updateProductNameDTO.name()
                        )
                )
                .flatMap(responseData ->
                        ServerResponse.status(200)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,200,"Product newName updated successfully"))
                );
    }

    public Mono<ServerResponse> listenPUTUpdateStock(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UpdateProductStockDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Product stock info is required")))
                .flatMap(serverRequestValidator::validate)
                .flatMap(updateProductStockDTO ->
                        updateProductStockUseCase.updateStock(
                                updateProductStockDTO.id(),
                                updateProductStockDTO.stock()
                        )
                )
                .flatMap(responseData ->
                        ServerResponse.status(200)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,200,"Product updated successfully"))
                );
    }

    public Mono<ServerResponse> listenDELETEProduct(ServerRequest serverRequest){
        return Mono.justOrEmpty(serverRequest.queryParam("product_id"))
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Product info is required")))
                .map(UUID::fromString)
                .flatMap(deleteProductUseCase::delete)
                .then(
                        ServerResponse.status(200)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>("",200,"Product deleted successfully"))
                );
    }

}
