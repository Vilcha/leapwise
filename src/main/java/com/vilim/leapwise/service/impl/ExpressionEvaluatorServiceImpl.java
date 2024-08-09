package com.vilim.leapwise.service.impl;

import com.vilim.leapwise.exception.ExpressionEvaluationException;
import com.vilim.leapwise.mapper.ExpressionMapper;
import com.vilim.leapwise.model.entity.ExpressionEntity;
import com.vilim.leapwise.model.request.EvaluationRequest;
import com.vilim.leapwise.model.request.ExpressionSaveRequest;
import com.vilim.leapwise.model.tree.ExpressionNode;
import com.vilim.leapwise.repo.ExpressionRepository;
import com.vilim.leapwise.service.ExpressionEvaluatorService;
import com.vilim.leapwise.utils.ExpressionUtil;
import com.vilim.leapwise.utils.JsonUtil;
import com.vilim.leapwise.utils.ParserUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ExpressionEvaluatorServiceImpl implements ExpressionEvaluatorService {

    private final ExpressionRepository expressionRepository;
    private final ExpressionMapper expressionMapper;
    private final ExpressionUtil expressionUtil;

    @Override
    @Transactional
    public ResponseEntity<Long> createExpression(final ExpressionSaveRequest expression) {
        ExpressionEntity savedEntity = expressionRepository.save(expressionMapper.toExpressionEntity(expression));
        log.info("Entity successfully saved: {}", savedEntity);
        return new ResponseEntity<>(savedEntity.getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Boolean> evaluateExpression(final Long id, final EvaluationRequest request) {
        ExpressionEntity expressionEntity = expressionUtil.checkIfExpressionExists(id);
        try {
            Map<String, String> flatJson = JsonUtil.toFlatMap(request.getCustomer());
            ExpressionNode rootNode = ParserUtil.getRootNode(expressionEntity.getExpression());
            boolean result = rootNode.evaluate(flatJson);
            log.info("Evaluation result: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ExpressionEvaluationException(e.getMessage());
        }
    }
}
