package com.peopleManagement.controllers;

import com.peopleManagement.dtos.AddressDTO;
import com.peopleManagement.services.generics.IAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Endpoints: Endereço", description = "CRUD de Endereços")
public class AddressController {
    private final IAddressService service;
    public AddressController(IAddressService service) {
        this.service = service;
    }

    @GetMapping("/get")
    @Operation(summary = "Recupera todos os endereços",
            description = "GET REQUEST para recuperar uma collection de endereços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem sucedida")
    })
    public ResponseEntity<Collection<AddressDTO>> getAllAddresses() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Recupera endereço pelo ID",
            description = "GET REQUEST para recuperar um endereço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem sucedida")
    })
    public ResponseEntity<AddressDTO> getAddressById(
            @Parameter(description = "ID do endereço", in = ParameterIn.PATH, required = true) @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PostMapping("/save")
    @Operation(summary = "Salva um novo endereço",
            description = "POST REQUEST para salvar um novo endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição bem sucedida")
    })
    public ResponseEntity<AddressDTO> saveNewAddress(@RequestBody @Valid AddressDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Atualiza um endereço",
            description = "PUT REQUEST para atualizar um endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem sucedida")
    })
    public ResponseEntity<AddressDTO> editAddressById(
            @Parameter(description = "ID do endereço", in = ParameterIn.PATH, required = true) @PathVariable Long id,
            @RequestBody @Valid AddressDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deleta um endereço",
            description = "DELETE REQUEST para deletar um endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Requisição bem sucedida")
    })
    public ResponseEntity<Boolean> deleteAddressById(
            @Parameter(description = "ID do endereço", in = ParameterIn.PATH, required = true)
            @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }
}
