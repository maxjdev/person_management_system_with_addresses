package com.peopleManagement.services.generics;

import com.peopleManagement.exceptions.PersonNotFoundException;
import com.peopleManagement.dtos.AddressDTO;
import com.peopleManagement.dtos.PersonDTO;

public interface IPersonService extends IGenericCrud<PersonDTO, Long> {
    boolean addAddress(Long idPerson, AddressDTO dto) throws PersonNotFoundException;
    boolean removeAddress(Long idPerson, Long idAddress) throws PersonNotFoundException;
    void setMainAddress(Long idPerson, Long idAddress) throws PersonNotFoundException;
    AddressDTO getMainAddress(Long idPerson) throws Exception;
}
