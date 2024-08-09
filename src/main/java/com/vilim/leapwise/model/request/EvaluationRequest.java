package com.vilim.leapwise.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationRequest {

    @Schema(
            description = "Details of the customer.",
            example = "{ \"firstName\": \"JOHN\", \"lastName\": \"DOE\", "
                    + "\"address\": { \"city\": \"Chicago\", \"zipCode\": 1234, "
                    + "\"street\": \"56th\", \"houseNumber\": 2345 }, "
                    + "\"salary\": 50000, \"type\": \"BUSINESS\" }"
    )
    @NotNull
    @Valid
    private CustomerRequest customer;
}
