package com.peopleManagement.services.impl;

import com.peopleManagement.domain.model.Address;
import com.peopleManagement.domain.model.Person;
import com.peopleManagement.domain.repositories.IAddressRepository;
import com.peopleManagement.domain.repositories.IPersonRepository;
import com.peopleManagement.dtos.AddressDTO;
import com.peopleManagement.dtos.PersonDTO;
import com.peopleManagement.exceptions.AddressNotFoundException;
import com.peopleManagement.exceptions.PersonNotFoundException;
import com.peopleManagement.services.generics.IPersonService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements IPersonService {
    private final IPersonRepository repository;
    private final IAddressRepository addressRepository;

    public PersonServiceImpl(IPersonRepository repository, IAddressRepository addressRepository) {
        this.repository = repository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Collection<PersonDTO> findAll() {
        List<Person> persons = repository.findAll();
        return persons.stream()
                .map(PersonDTO::personToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO findById(Long id) throws PersonNotFoundException {
        var person = findPerson(id);
        return PersonDTO.personToDTO(person);
    }

    @Override
    public PersonDTO save(PersonDTO person) {
        return PersonDTO.personToDTO(repository.save(PersonDTO.dtoToPerson(person)));
    }

    @Override
    public PersonDTO update(Long id, PersonDTO updatedPerson) throws PersonNotFoundException {
        var person = findPerson(id);
        person.setFullName(updatedPerson.fullName());
        person.setDateBirth(updatedPerson.dateBirth());
        return PersonDTO.personToDTO(repository.save(person));
    }

    @Override
    public boolean delete(Long id) throws PersonNotFoundException {
        var person = findPerson(id);
        repository.delete(person);
        return !repository.existsById(id);
    }

    @Override
    public boolean addAddress(Long idPerson, AddressDTO dto) throws PersonNotFoundException {
        var person = findPerson(idPerson);
        boolean added = person.addAddress(AddressDTO.dtoToAddress(dto));
        repository.save(person);
        return added;
    }

    @Override
    public boolean removeAddress(Long idPerson, Long idAddress) throws PersonNotFoundException {
        var person = findPerson(idPerson);
        boolean removed = person.removeAddress(idAddress);
        repository.save(person);
        return removed;
    }

    @Override
    public void setMainAddress(Long idPerson, Long idAddress) throws PersonNotFoundException {
        var person = findPerson(idPerson);
        person.setIdMainAddress(idAddress);
        repository.save(person);
    }

    @Override
    public AddressDTO getMainAddress(Long idPerson) {
        var person = findPerson(idPerson);
        var address = findAddress(person.getIdMainAddress());
        return AddressDTO.addressToDTO(address);
    }

    private Person findPerson(Long id) throws PersonNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private Address findAddress(Long id) throws AddressNotFoundException {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }
}
