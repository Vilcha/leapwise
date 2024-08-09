package com.vilim.leapwise.service.impl;

import com.vilim.leapwise.mapper.ExpressionMapper;
import com.vilim.leapwise.model.entity.ExpressionEntity;
import com.vilim.leapwise.model.request.ExpressionSaveRequest;
import com.vilim.leapwise.repo.ExpressionRepository;
import com.vilim.leapwise.utils.ExpressionUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpressionEvaluatorServiceImplTest {

    @Mock
    ExpressionRepository expressionRepository;

    @Mock
    ExpressionMapper expressionMapper;

    @Mock
    ExpressionUtil expressionUtil;

    @InjectMocks
    ExpressionEvaluatorServiceImpl expressionEvaluatorServiceImpl;

    @Test
    void createExpression_success() {
        ExpressionSaveRequest request = new ExpressionSaveRequest("testName", "testValue");
        ExpressionEntity expressionEntity = new ExpressionEntity();
        expressionEntity.setId(1L);

        when(expressionMapper.toExpressionEntity(request)).thenReturn(expressionEntity);
        when(expressionRepository.save(expressionEntity)).thenReturn(expressionEntity);

        ResponseEntity<Long> response = expressionEvaluatorServiceImpl.createExpression(request);

        verify(expressionRepository).save(expressionEntity);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expressionEntity.getId(), response.getBody());
    }

    @Test
    void evaluateExpression() {
    }
}