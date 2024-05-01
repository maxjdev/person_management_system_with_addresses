package com.peopleManagement.dtos;

import com.peopleManagement.domain.model.Address;
import com.peopleManagement.domain.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDTOTest {
    @Test
    public void testPersonToDTO() {
        Address address1 = Address.builder()
                .street("Rua A")
                .city("Cidade X")
                .number(123)
                .zipCode("11111-111")
                .state("Estado Y")
                .build();

        Set<Address> addresses = new HashSet<>();
        addresses.add(address1);

        Person person = Person.builder()
                .fullName("Fulano de Tal")
                .dateBirth(LocalDate.of(1990, 1, 1))
                .addresses(addresses)
                .build();

        PersonDTO personDTO = PersonDTO.personToDTO(person);

        assertEquals("Fulano de Tal", personDTO.fullName());
        assertEquals(LocalDate.of(1990, 1, 1), personDTO.dateBirth());
        assertEquals(1, personDTO.addresses().size());
        assertEquals("Rua A", personDTO.addresses().iterator().next().street());
    }

    @Test
    public void testDtoToPerson() {
        AddressDTO addressDTO = AddressDTO.builder()
                .street("Rua A")
                .city("Cidade X")
                .number(123)
                .zipCode("11111-111")
                .state("Estado Y")
                .build();

        PersonDTO personDTO = PersonDTO.builder()
                .fullName("Fulano de Tal")
                .dateBirth(LocalDate.of(1990, 1, 1))
                .addresses(Set.of(addressDTO))
                .build();

        Person person = PersonDTO.dtoToPerson(personDTO);

        assertEquals("Fulano de Tal", person.getFullName());
        assertEquals(LocalDate.of(1990, 1, 1), person.getDateBirth());
        assertEquals(1, person.getAddresses().size());
        assertEquals("Rua A", person.getAddresses().iterator().next().getStreet());
    }
}
