package com.accenture.assessment.config;

import com.accenture.assessment.exceptions.BusinessException;
import com.accenture.assessment.exceptions.InternalSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
@Slf4j
public class R2dbcErrorMapper {
    public <T> Function<Mono<T>, Mono<T>> onErrorMap() {
        return mono -> mono
                .doOnError(ex -> {
                    if (!(ex instanceof BusinessException))
                        log.error("R2DBC Infrastructure Error: {}", ex.getMessage(), ex);
                    else
                        log.debug("Other error occurred: {}", ex.getMessage(), ex);
                })
                .onErrorMap(DataIntegrityViolationException.class,
                        ex -> new BusinessException("The modifying object already exists in the system"))
                .onErrorMap(ex -> !(ex instanceof BusinessException),
                        ex -> new InternalSystemException("Unexpected internal system error...", ex));
    }
}
