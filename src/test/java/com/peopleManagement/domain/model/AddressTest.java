package com.peopleManagement.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    private Address address;

    @BeforeEach
    public void setup() {
        address = Address.builder()
                .id(1L)
                .street("Rua Bandeirantes")
                .zipCode("12345-678")
                .city("Sao Paulo")
                .state("SP")
                .number(50)
                .build();
    }

    @Test
    public void createAddressTest() {
        Address newAddress =Address.builder()
                .id(address.getId())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .state(address.getState())
                .number(address.getNumber())
                .build();

        assertNotNull(newAddress);
        assertEquals(address.getId(), newAddress.getId());
        assertEquals(address.getStreet(), newAddress.getStreet());
        assertEquals(address.getZipCode(), newAddress.getZipCode());
        assertEquals(address.getCity(), newAddress.getCity());
        assertEquals(address.getState(), newAddress.getState());
        assertEquals(address.getNumber(), newAddress.getNumber());
    }

    @Test
    public void constructorWithoutIdTest() {
        Address newAddress = Address.builder()
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .state(address.getState())
                .number(address.getNumber())
                .build();

        assertNotNull(newAddress);
        assertNull(newAddress.getId());
        assertEquals(newAddress.getStreet(), address.getStreet());
        assertEquals(newAddress.getZipCode(), address.getZipCode());
        assertEquals(newAddress.getCity(), address.getCity());
        assertEquals(newAddress.getState(), address.getState());
        assertEquals(newAddress.getNumber(), address.getNumber());
    }

    @Test
    public void setterAndGetterMethodsTest() {
        Address newAddress = new Address();

        newAddress.setId(address.getId());
        newAddress.setStreet(address.getStreet());
        newAddress.setZipCode(address.getZipCode());
        newAddress.setCity(address.getCity());
        newAddress.setState(address.getState());
        newAddress.setNumber(address.getNumber());

        assertEquals(address.getId(), newAddress.getId());
        assertEquals(address.getStreet(), newAddress.getStreet());
        assertEquals(address.getZipCode(), newAddress.getZipCode());
        assertEquals(address.getCity(), newAddress.getCity());
        assertEquals(address.getState(), newAddress.getState());
        assertEquals(address.getNumber(), newAddress.getNumber());
    }
}
