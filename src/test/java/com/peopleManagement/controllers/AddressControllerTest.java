package com.peopleManagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peopleManagement.dtos.AddressDTO;
import com.peopleManagement.services.generics.IAddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private IAddressService service;
    private AddressDTO dto;

    @Before
    public void setup() {
        dto = AddressDTO.builder()
                .street("Rua Bandeirantes")
                .number(50)
                .zipCode("12345-678")
                .city("Sao Paulo")
                .state("SP")
                .build();
    }

    @Test
    public void getAllAddressesTest() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/addresses/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAddressByIdTest() throws Exception {
        when(service.findById(anyLong()))
                .thenReturn(dto);

        mockMvc.perform(get("/addresses/get/{id}", anyLong())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void saveNewAddressTest() throws Exception {
        when(service.save(any(AddressDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/addresses/save")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.street").value(dto.street()))
                .andExpect(jsonPath("$.number").value(dto.number()))
                .andExpect(jsonPath("$.zipCode").value(dto.zipCode()))
                .andExpect(jsonPath("$.city").value(dto.city()))
                .andExpect(jsonPath("$.state").value(dto.state()));
    }

    @Test
    public void editAddressByIdTest() throws Exception {
        var newAddressDTO = AddressDTO.builder()
                .street("Rua Atualizada")
                .number(100)
                .zipCode("87654-321")
                .city("Rio de Janeiro")
                .state("RJ")
                .build();

        when(service.update(anyLong(), any(AddressDTO.class))).thenReturn(newAddressDTO);

        mockMvc.perform(put("/addresses/edit/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(newAddressDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value(newAddressDTO.street()))
                .andExpect(jsonPath("$.number").value(newAddressDTO.number()))
                .andExpect(jsonPath("$.zipCode").value(newAddressDTO.zipCode()))
                .andExpect(jsonPath("$.city").value(newAddressDTO.city()))
                .andExpect(jsonPath("$.state").value(newAddressDTO.state()));
    }

    @Test
    public void deleteAddressByIdTest() throws Exception {
        when(service.delete(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/addresses/delete/{id}", anyLong()))
                .andExpect(status().isNoContent());
    }
}

