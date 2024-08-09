package com.vilim.leapwise.service;

import com.vilim.leapwise.model.request.EvaluationRequest;
import com.vilim.leapwise.model.request.ExpressionSaveRequest;
import org.springframework.http.ResponseEntity;

public interface ExpressionEvaluatorService {

    ResponseEntity<Long> createExpression(ExpressionSaveRequest expression);

    ResponseEntity<Boolean> evaluateExpression(Long id, EvaluationRequest request);
}
