package com.peopleManagement.dtos;

import com.peopleManagement.domain.model.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record PersonDTO(
        @NotBlank(message = "Completing the FULL-NAME field is mandatory.")
        @Size(min = 3, max = 50)
        @Schema(example = "Maria da Silva")
        String fullName,
        @Past(message = "The DATE-OF-BIRTH must be in the past.")
        @Schema(example = "2020-04-04")
        LocalDate dateBirth,
        @Schema(hidden = true)
        Long idMainAddress,
        @NotNull
        Set<AddressDTO> addresses
) {
    public static PersonDTO personToDTO(Person e) {
        return PersonDTO.builder()
                .fullName(e.getFullName())
                .dateBirth(e.getDateBirth())
                .idMainAddress(e.getIdMainAddress())
                .addresses(e.getAddresses().stream()
                        .map(AddressDTO::addressToDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Person dtoToPerson(PersonDTO e) {
        return Person.builder()
                .fullName(e.fullName)
                .dateBirth(e.dateBirth)
                .addresses(e.addresses.stream()
                        .map(AddressDTO::dtoToAddress)
                        .collect(Collectors.toSet()))
                .build();
    }
}
