package com.vilim.leapwise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vilim.leapwise.model.request.CustomerRequest;
import com.vilim.leapwise.model.request.EvaluationRequest;
import com.vilim.leapwise.model.request.ExpressionSaveRequest;
import com.vilim.leapwise.service.ExpressionEvaluatorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpressionEvaluatorController.class)
class ExpressionEvaluatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpressionEvaluatorService expressionEvaluatorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createExpression_withFailedParameterValidation() throws Exception {
        ExpressionSaveRequest request = new ExpressionSaveRequest(null, null);

        mockMvc.perform(post("/api/expression")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("Cannot be null")));
    }

    @Test
    void evaluateExpression_withCustomerSetToNull() throws Exception {
        Long id = 1L;
        EvaluationRequest command = new EvaluationRequest();

        mockMvc.perform(post("/api/evaluate/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("customer - must not be null")));
    }

    @Test
    void evaluateExpression_withAddressSetToNull() throws Exception {
        Long id = 1L;
        EvaluationRequest command = new EvaluationRequest(
                new CustomerRequest("John", "Doe", null, 900, "Marketing"));

        mockMvc.perform(post("/api/evaluate/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("address - must not be null")));
    }
}