package com.peopleManagement.dtos;

import com.peopleManagement.domain.model.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AddressDTO(
        @NotBlank(message = "Completing the STREET field is mandatory.")
        @Size(min = 3, max = 100)
        @Schema(example = "Rua Bandeirantes")
        String street,
        @NotBlank(message = "Completing the ZIP-CODE field is mandatory.")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP must be in the format: 11111-111.")
        @Schema(example = "11111-111")
        String zipCode,
        @Positive
        @Schema(example = "20")
        int number,
        @NotBlank(message = "Completing the CITY field is mandatory.")
        @Size(min = 3, max = 100)
        @Schema(example = "Sao Paulo")
        String city,
        @NotBlank(message = "Completing the STATE field is mandatory.")
        @Size(min = 2, max = 2)
        @Schema(example = "SP")
        String state
) {
    public static AddressDTO addressToDTO(Address e) {
        return AddressDTO.builder()
                .street(e.getStreet())
                .zipCode(e.getZipCode())
                .number(e.getNumber())
                .city(e.getCity())
                .state(e.getState())
                .build();
    }

    public static Address dtoToAddress(AddressDTO e) {
        return Address.builder()
                .street(e.street)
                .zipCode(e.zipCode)
                .number(e.number)
                .city(e.city)
                .state(e.state)
                .build();
    }
}
