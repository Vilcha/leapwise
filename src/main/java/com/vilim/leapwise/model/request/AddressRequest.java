package com.vilim.leapwise.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressRequest {
    private String city;
    private Integer zipCode;
    private String street;
    private Integer houseNumber;
}
