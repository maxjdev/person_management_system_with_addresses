package com.peopleManagement.services.impl;

import com.peopleManagement.domain.model.Address;
import com.peopleManagement.domain.repositories.IAddressRepository;
import com.peopleManagement.dtos.AddressDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {
    @Mock
    private IAddressRepository repo;
    @InjectMocks
    private AddressServiceImpl service;
    private AddressDTO dto;
    private Address address;

    @Before
    public void setup() {
        dto = AddressDTO.builder()
                .street("Rua Bandeirantes")
                .number(99)
                .zipCode("12345-678")
                .city("Sao Paulo")
                .state("SP")
                .build();
        address = AddressDTO.dtoToAddress(dto);
    }

    @Test
    public void saveTest() {
        when(repo.save(any(Address.class))).thenReturn(address);

        var savedAddress = service.save(dto);

        assertNotNull(savedAddress);
        assertEquals(dto.zipCode(), savedAddress.zipCode());
    }

    @Test
    public void findAllTest() {
        List<Address> list = new ArrayList<>();
        list.add(address);

        when(repo.findAll()).thenReturn(list);

        var foundAddresses = service.findAll();

        assertNotNull(foundAddresses);
        assertEquals(1, foundAddresses.size());
    }

    @Test
    public void findByIdTest() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(address));

        var foundAddress = service.findById(1L);

        assertNotNull(foundAddress);
        assertEquals(dto.zipCode(), foundAddress.zipCode());
    }

    @Test
    public void updateTest() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(address));

        var updatedAddressDTO = AddressDTO.builder()
                .street("Rua Atualizada")
                .number(100)
                .zipCode("12345-678")
                .city("Sao Paulo")
                .state("SP")
                .build();

        when(repo.save(any(Address.class))).thenReturn(AddressDTO.dtoToAddress(updatedAddressDTO));

        var updatedAddress = service.update(1L, updatedAddressDTO);

        assertNotNull(updatedAddress);
        assertEquals(updatedAddressDTO.zipCode(), updatedAddress.zipCode());
    }

    @Test
    public void deleteTest() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(address));
        when(repo.existsById(anyLong())).thenReturn(false);

        var isDeleted = service.delete(1L);

        assertTrue(isDeleted);
    }
}
