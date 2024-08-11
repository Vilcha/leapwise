package com.vilim.leapwise.utils;

import com.vilim.leapwise.model.entity.ExpressionEntity;
import com.vilim.leapwise.repo.ExpressionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class ExpressionUtil {

    private final ExpressionRepository expressionRepository;

    public ExpressionEntity checkIfExpressionExists(final Long id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
        return expressionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expression entity with ID " + id + " not found"));
    }
}
