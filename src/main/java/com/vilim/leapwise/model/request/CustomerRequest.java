package com.vilim.leapwise.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerRequest {

    private String firstName;
    private String lastName;
    @NotNull
    @Valid
    private AddressRequest address;
    private Number salary;
    private String type;
}
