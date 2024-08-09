package com.vilim.leapwise.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpressionSaveRequest {

    @Schema(example = "Complex logical expression")
    @NotNull(message = "Cannot be null")
    private String name;

    @Schema(example = "customer.firstName == \"JOHN\"' && customer.salary < 100 "
            + "OR customer.address != null && customer.address.city == \"Washington\"")
    @NotNull(message = "Cannot be null")
    private String value;
}
