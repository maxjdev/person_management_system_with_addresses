package com.peopleManagement.domain.model;

import com.peopleManagement.exceptions.AddressAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    private Person person;
    private Address address;

    @BeforeEach
    public void setup() {
        address = Address.builder()
                .id(1L)
                .street("Rua Bandeirantes")
                .city("Sao Paulo")
                .state("SP")
                .number(50)
                .zipCode("12345-678")
                .build();

        person = Person.builder()
                .id(1L)
                .fullName("Maria da Silva")
                .dateBirth(LocalDate.of(1990, 5, 15))
                .addresses(new HashSet<>())
                .build();
    }

    @Test
    public void addAndRemoveAddressTest() {
        assertTrue(person.addAddress(address));
        assertThrows(AddressAlreadyExistsException.class,
                () -> person.addAddress(address));

        assertTrue(person.removeAddress(address.getId()));
        assertFalse(person.removeAddress(address.getId()));
    }

    @Test
    public void setIdMainAddressAndGetIdMainAddressTest() {
        assertDoesNotThrow(
                () -> person.setIdMainAddress(address.getId())
        );
        assertEquals(address.getId(), person.getIdMainAddress());
    }

    @Test
    public void gettersAndSettersTest() {
        person.addAddress(address);

        Person newPerson = new Person();
        Set<Address> addresses = Set.of(address);

        newPerson.setId(person.getId());
        newPerson.setFullName(person.getFullName());
        newPerson.setDateBirth(person.getDateBirth());
        newPerson.setAddresses(addresses);

        assertEquals(person.getId(), newPerson.getId());
        assertEquals(person.getFullName(), newPerson.getFullName());
        assertEquals(person.getDateBirth(), newPerson.getDateBirth());
        assertEquals(person.getAddresses(), newPerson.getAddresses());
    }
}
