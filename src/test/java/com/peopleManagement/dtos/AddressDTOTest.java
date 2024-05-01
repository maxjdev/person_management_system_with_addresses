package com.peopleManagement.dtos;

import com.peopleManagement.domain.model.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressDTOTest {
    @Test
    public void testAddressToDTO() {
        Address address = Address.builder()
                .street("Rua Bandeirantes")
                .zipCode("11111-111")
                .number(20)
                .city("Sao Paulo")
                .state("SP")
                .build();

        AddressDTO addressDTO = AddressDTO.addressToDTO(address);

        assertEquals("Rua Bandeirantes", addressDTO.street());
        assertEquals("11111-111", addressDTO.zipCode());
        assertEquals(20, addressDTO.number());
        assertEquals("Sao Paulo", addressDTO.city());
        assertEquals("SP", addressDTO.state());
    }

    @Test
    public void testDtoToAddress() {
        AddressDTO addressDTO = AddressDTO.builder()
                .street("Rua Bandeirantes")
                .zipCode("11111-111")
                .number(20)
                .city("Sao Paulo")
                .state("SP")
                .build();

        Address address = AddressDTO.dtoToAddress(addressDTO);

        assertEquals("Rua Bandeirantes", address.getStreet());
        assertEquals("11111-111", address.getZipCode());
        assertEquals(20, address.getNumber());
        assertEquals("Sao Paulo", address.getCity());
        assertEquals("SP", address.getState());
    }
}
