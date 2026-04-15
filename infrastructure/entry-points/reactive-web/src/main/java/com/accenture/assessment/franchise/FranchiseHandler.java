package com.accenture.assessment.franchise;

import com.accenture.assessment.dto.ApiResponse;
import com.accenture.assessment.exception.ServerRequestValidationException;
import com.accenture.assessment.franchise.dto.CreateFranchiseDTO;
import com.accenture.assessment.franchise.dto.UpdateFranchiseNameDTO;
import com.accenture.assessment.franchise.usecase.CreateFranchiseUseCase;
import com.accenture.assessment.franchise.usecase.GetTopStockProductsUseCase;
import com.accenture.assessment.franchise.usecase.UpdateFranchiseUseCase;
import com.accenture.assessment.validator.ServerRequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@AllArgsConstructor
public class FranchiseHandler {
    private final ServerRequestValidator serverRequestValidator;
    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseUseCase updateFranchiseUseCase;
    private final GetTopStockProductsUseCase getTopStockProductsUseCase;

    public Mono<ServerResponse> listenPOSTSave(ServerRequest serverRequest){
        return serverRequest.bodyToMono(CreateFranchiseDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Franchise can't be empty")))
                .flatMap(serverRequestValidator::validate)
                .map(CreateFranchiseDTO::name)
                .flatMap(createFranchiseUseCase::createFranchise)
                .flatMap(responseData ->
                        ServerResponse.status(201)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,201,"Franchise created successfully"))
                );
    }

    public Mono<ServerResponse> listenPUTUpdateName(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UpdateFranchiseNameDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Franchise info is required")))
                .flatMap(serverRequestValidator::validate)
                .flatMap(updateFranchiseNameDTO ->
                        updateFranchiseUseCase.update(
                                updateFranchiseNameDTO.id(),
                                updateFranchiseNameDTO.newName()
                        )
                )
                .flatMap(responseData ->
                        ServerResponse.status(200)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,200,"Franchise updated successfully"))
                );
    }

    public Mono<ServerResponse> listenGETTopStockProducts(ServerRequest serverRequest){
        return Mono.justOrEmpty(serverRequest.queryParam("franchise_Id"))
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Franchise info is required")))
                .map(UUID::fromString)
                .flatMap(getTopStockProductsUseCase::getTopStockProducts)
                .flatMap(responseData ->
                        ServerResponse.status(200)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData, 200, "Top stock products report successfully"))
                );
    }

}
