package com.accenture.assessment.branch;

import com.accenture.assessment.branch.dto.CreateBranchDTO;
import com.accenture.assessment.branch.dto.UpdateBranchNameDTO;
import com.accenture.assessment.branch.usecase.CreateBranchUseCase;
import com.accenture.assessment.branch.usecase.UpdateBranchUseCase;
import com.accenture.assessment.dto.ApiResponse;
import com.accenture.assessment.exception.ServerRequestValidationException;
import com.accenture.assessment.validator.ServerRequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@AllArgsConstructor
public class BranchHandler {
    private final ServerRequestValidator serverRequestValidator;
    private final CreateBranchUseCase createBranchUseCase;
    private final UpdateBranchUseCase updateBranchUseCase;

    public Mono<ServerResponse> listenPOSTSave(ServerRequest serverRequest){
        return serverRequest.bodyToMono(CreateBranchDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Branch info is required")))
                .flatMap(serverRequestValidator::validate)
                .flatMap(createBranchDTO ->
                        createBranchUseCase.create(createBranchDTO.franchiseId(), createBranchDTO.name())
                )
                .flatMap(responseData ->
                        ServerResponse.status(201)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,201,"Branch created successfully"))
                );
    }

    public Mono<ServerResponse> listenPUTUpdateName(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UpdateBranchNameDTO.class)
                .switchIfEmpty(Mono.error(new ServerRequestValidationException("Branch info is required")))
                .flatMap(serverRequestValidator::validate)
                .flatMap(updateBranchNameDTO ->
                        updateBranchUseCase.update(
                                updateBranchNameDTO.id(),
                                updateBranchNameDTO.name()
                        )
                )
                .flatMap(responseData ->
                        ServerResponse.status(200)
                                .contentType(APPLICATION_JSON)
                                .bodyValue(new ApiResponse<>(responseData,200,"Branch updated successfully"))
                );
    }

}
