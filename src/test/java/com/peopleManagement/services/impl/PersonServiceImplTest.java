package com.peopleManagement.services.impl;

import com.peopleManagement.domain.model.Person;
import com.peopleManagement.domain.repositories.IAddressRepository;
import com.peopleManagement.domain.repositories.IPersonRepository;
import com.peopleManagement.dtos.AddressDTO;
import com.peopleManagement.dtos.PersonDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {
    @Mock
    private IPersonRepository repo;
    @Mock
    private IAddressRepository adRepo;
    @InjectMocks
    private PersonServiceImpl service;
    private PersonDTO dto;
    private Person person;
    private AddressDTO addressDTO;

    @Before
    public void setup() {
        addressDTO = AddressDTO.builder()
                .street("Rua Bandeirantes")
                .number(90)
                .zipCode("12345-678")
                .city("Sao Paulo")
                .state("SP")
                .build();

        dto = PersonDTO.builder()
                .fullName("Maria da Silva")
                .dateBirth(
                        LocalDate.of(2000, 6, 20))
                .addresses(Set.of(addressDTO))
                .build();

        person = PersonDTO.dtoToPerson(dto);
    }

    @Test
    public void saveTest() {
        when(repo.save(any(Person.class))).thenReturn(person);

        var savedPerson = service.save(dto);

        assertNotNull(savedPerson);
        assertEquals(dto.fullName(), savedPerson.fullName());
    }

    @Test
    public void findAllTest() {
        List<Person> list = new ArrayList<>();
        list.add(person);

        when(repo.findAll()).thenReturn(list);

        var foundPersons = service.findAll();

        assertNotNull(foundPersons);
        assertEquals(1, foundPersons.size());
    }

    @Test
    public void findById() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(person));

        var foundPerson = service.findById(1L);

        assertNotNull(foundPerson);
        assertEquals(dto.fullName(), foundPerson.fullName());
    }

    @Test
    public void updateTest() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(person));

        var updatedPersonDTO = PersonDTO.builder()
                .fullName("Maria da Silva Atualizada")
                .dateBirth(
                        LocalDate.of(1995, 6, 20))
                .addresses(Set.of(addressDTO))
                .build();

        when(repo.save(any(Person.class))).thenReturn(PersonDTO.dtoToPerson(updatedPersonDTO));

        var updatedPerson = service.update(1L, updatedPersonDTO);

        assertNotNull(updatedPerson);
        assertEquals(updatedPersonDTO.fullName(), updatedPerson.fullName());
    }

    @Test
    public void deleteTest() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(person));
        when(repo.existsById(any(Long.class))).thenReturn(false);

        var isDeleted = service.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void addAddressTest() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(person));

        var newAddressDTO = AddressDTO.builder()
                .street("Rua Atualizada")
                .number(200)
                .zipCode("98765-432")
                .city("Rio de Janeiro")
                .state("RJ")
                .build();

        var isAdded = service.addAddress(1L, newAddressDTO);

        assertTrue(isAdded);
        assertEquals(2, person.getAddresses().size());
    }

    @Test
    public void setMainAddress() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(person));

        assertDoesNotThrow(() -> service.setMainAddress(1L, 1L));
        assertEquals(1L, person.getIdMainAddress());
    }

    @Test
    public void removeAddressTest() {
        Person personMock = mock(Person.class);

        when(repo.findById(anyLong())).thenReturn(Optional.of(personMock));
        when(personMock.removeAddress(anyLong())).thenReturn(true);
        when(repo.save(personMock)).thenReturn(personMock);

        var isRemoved = service.removeAddress(1L, 10L);

        assertTrue(isRemoved);
    }

    @Test
    public void getMainAddressTest() {
        var mainAddress = AddressDTO.dtoToAddress(addressDTO);

        when(repo.findById(anyLong())).thenReturn(Optional.of(person));
        when(adRepo.findById(mainAddress.getId())).thenReturn(Optional.of(mainAddress));

        person.setIdMainAddress(mainAddress.getId());
        var mainAddressDTO = service.getMainAddress(1L);

        assertNotNull(mainAddressDTO);
        assertEquals(addressDTO.street(), mainAddressDTO.street());
    }
}
