package com.vilim.leapwise.controller;

import com.vilim.leapwise.model.request.EvaluationRequest;
import com.vilim.leapwise.model.request.ExpressionSaveRequest;
import com.vilim.leapwise.service.ExpressionEvaluatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ExpressionEvaluatorController {

    private final ExpressionEvaluatorService expressionEvaluatorService;

    @Operation(summary = "Creates and stores a new logical expression with its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expression successfully created"),
            @ApiResponse(responseCode = "400", description = "Data integrity or validation error")
    })
    @PostMapping("/expression")
    public ResponseEntity<Long> createExpression(@RequestBody @Valid final ExpressionSaveRequest request) {
        return expressionEvaluatorService.createExpression(request);
    }

    @Operation(summary = "Fetches a stored expression by the provided ID and evaluates it using the provided JSON object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expression successfully evaluated"),
            @ApiResponse(responseCode = "404", description = "Expression not found by given ID")
    })
    @PostMapping("/evaluate/{id}")
    public ResponseEntity<Boolean> evaluateExpression(@PathVariable final Long id,
                                                      @RequestBody @Valid final EvaluationRequest request) {
        return expressionEvaluatorService.evaluateExpression(id, request);
    }
}
