package com.peopleManagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peopleManagement.dtos.AddressDTO;
import com.peopleManagement.dtos.PersonDTO;
import com.peopleManagement.services.generics.IPersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private IPersonService service;
    private PersonDTO dto;
    private AddressDTO addressDTO;

    @Before
    public void setup() {
        addressDTO = AddressDTO.builder()
                .street("Rua Bandeirantes")
                .number(50)
                .zipCode("12345-678")
                .city("Sao Paulo")
                .state("SP")
                .build();
        dto = PersonDTO.builder()
                .fullName("Maria da Silva")
                .dateBirth(LocalDate.of(1995, 6, 25))
                .addresses(new HashSet<>())
                .build();
    }

    @Test
    public void getAllPersonsTest() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/persons/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonByIdTest() throws Exception {
        when(service.findById(anyLong())).thenReturn(dto);

        mockMvc.perform(get("/persons/get/{id}", anyLong())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void saveNewPersonTest() throws Exception {
        when(service.save(any(PersonDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/persons/save")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value(dto.fullName()))
                .andExpect(jsonPath("$.dateBirth").value(dto.dateBirth().toString()));
    }

    @Test
    public void editPersonByIdTest() throws Exception {
        var newPersonDTO = PersonDTO.builder()
                .fullName("Nome Atualizado")
                .dateBirth(LocalDate.of(2005, 10, 2))
                .addresses(new HashSet<>())
                .build();

        when(service.update(anyLong(), any(PersonDTO.class))).thenReturn(newPersonDTO);

        mockMvc.perform(put("/persons/edit/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(newPersonDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(newPersonDTO.fullName()))
                .andExpect(jsonPath("$.dateBirth").value(newPersonDTO.dateBirth().toString()));
    }

    @Test
    public void deletePersonByIdTest() throws Exception {
        when(service.delete(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/persons/delete/{id}", anyLong()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void addAddressAtPersonTest() throws Exception {
        when(service.addAddress(anyLong(), any(AddressDTO.class))).thenReturn(true);

        mockMvc.perform(post("/persons/addAddress/{idPerson}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(addressDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void removeAddressAtPersonTest() throws Exception {
        when(service.removeAddress(anyLong(), anyLong())).thenReturn(true);
        mockMvc.perform(delete("/persons/removeAddress/{idPerson}/{idAddress}", 1L, 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void setMainAddressAtPersonTest() throws Exception {
        doNothing().when(service).setMainAddress(anyLong(), anyLong());
        mockMvc.perform(put("/persons/setMainAddress/{idPerson}/{idAddress}", 1L, 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void getMainAddressByIdTest() throws Exception {
        when(service.getMainAddress(anyLong())).thenReturn(addressDTO);

        mockMvc.perform(get("/persons/getMainAddress/{idPerson}", 1L))
                .andExpect(status().isOk());
    }
}
