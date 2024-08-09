package com.vilim.leapwise.utils;

import com.vilim.leapwise.model.entity.ExpressionEntity;
import com.vilim.leapwise.repo.ExpressionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpressionUtilTest {

    @Mock
    private ExpressionRepository expressionRepository;

    @InjectMocks
    private ExpressionUtil expressionUtil;

    @Test
    void checkIfExpressionExists_withExistingID() {
        Long id = 1L;
        ExpressionEntity entity = new ExpressionEntity();
        when(expressionRepository.findById(id)).thenReturn(Optional.of(entity));

        ExpressionEntity result = expressionUtil.checkIfExpressionExists(id);
        assertEquals(entity, result);
    }

    @Test
    void checkIfExpressionExists_withNegativeID() {
        Long id = -1L;
        assertThrows(IllegalArgumentException.class, () -> {
            expressionUtil.checkIfExpressionExists(id);
        });
    }

    @Test
    void checkIfExpressionExists_withNonExistingID() {
        Long id = 99999L;
        when(expressionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            expressionUtil.checkIfExpressionExists(id);
        });
    }
}