package com.peopleManagement.services.impl;

import com.peopleManagement.domain.model.Address;
import com.peopleManagement.domain.repositories.IAddressRepository;
import com.peopleManagement.dtos.AddressDTO;
import com.peopleManagement.exceptions.AddressNotFoundException;
import com.peopleManagement.services.generics.IAddressService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements IAddressService {
    private final IAddressRepository repository;

    public AddressServiceImpl(IAddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<AddressDTO> findAll() {
        List<Address> addresses = repository.findAll();
        return addresses.stream()
                .map(AddressDTO::addressToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO findById(Long id) throws AddressNotFoundException {
        var address = findAddress(id);
        return AddressDTO.addressToDTO(address);
    }

    @Override
    public AddressDTO save(AddressDTO dto) {
        Address address = AddressDTO.dtoToAddress(dto);
        return AddressDTO.addressToDTO(repository.save(address));
    }

    @Override
    public AddressDTO update(Long id, AddressDTO updatedAddress) throws AddressNotFoundException {
        var address = findAddress(id);

        address.setStreet(updatedAddress.street());
        address.setCity(updatedAddress.city());
        address.setNumber(updatedAddress.number());
        address.setState(updatedAddress.state());
        address.setZipCode(updatedAddress.zipCode());

        return AddressDTO.addressToDTO(repository.save(address));
    }

    @Override
    public boolean delete(Long id) throws AddressNotFoundException {
        var address = findAddress(id);
        repository.delete(address);
        return !repository.existsById(id);
    }

    private Address findAddress(Long id) throws AddressNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }
}
