package com.vilim.leapwise.utils;

import com.vilim.leapwise.model.request.AddressRequest;
import com.vilim.leapwise.model.request.CustomerRequest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {


    @Test
    void toFlatMap_transformCustomerJsonToMap() throws IllegalAccessException {
        AddressRequest address = new AddressRequest("Chicago", 123, "4th Main Street", 12);
        CustomerRequest customer = new CustomerRequest("John", "Doe", address, 900, "Business");

        Map<String, String> flatJson = JsonUtil.toFlatMap(customer);

        Map<String, String> expected = new HashMap<>();
        expected.put("customer.firstName", "John");
        expected.put("customer.lastName", "Doe");
        expected.put("customer.address.city", "Chicago");
        expected.put("customer.address.zipCode", "123");
        expected.put("customer.address.street", "4th Main Street");
        expected.put("customer.address.houseNumber", "12");
        expected.put("customer.salary", "900");
        expected.put("customer.type", "Business");

        assertEquals(expected, flatJson);
    }
}