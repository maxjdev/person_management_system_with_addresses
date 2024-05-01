package com.peopleManagement.controllers;

import com.peopleManagement.dtos.AddressDTO;
import com.peopleManagement.dtos.PersonDTO;
import com.peopleManagement.services.generics.IPersonService;
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
@RequestMapping("/persons")
@Tag(name = "Endpoints: Pessoa",
        description = "CRUD Pessoas | Definição e recuperação endereço principal de uma pessoa | Adiciona e remove endereços da lista de uma pessoa")
public class PersonController {
    private final IPersonService service;
    public PersonController(IPersonService service) {
        this.service = service;
    }

    @GetMapping("/get")
    @Operation(summary = "Recupera todas as pessoas", description = "GET REQUEST para recuperar uma collection de pessoas")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Requisição bem sucedida")})
    public ResponseEntity<Collection<PersonDTO>> getAllPersons() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Recupera pessoa pelo ID", description = "GET REQUEST para recuperar uma pessoa pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Requisição bem sucedida")})
    public ResponseEntity<PersonDTO> getPersonById(@Parameter(description = "ID da pessoa", in = ParameterIn.PATH, required = true)
                                                   @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PostMapping("/save")
    @Operation(summary = "Cria uma nova pessoa", description = "POST REQUEST para cria uma nova pessoa")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Requisição bem sucedida")})
    public ResponseEntity<PersonDTO> saveNewPerson(@RequestBody @Valid PersonDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Atualiza uma pessoa pelo ID", description = "PUT REQUEST para atualizar uma pessoa pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Requisição bem sucedida")})
    public ResponseEntity<PersonDTO> editPersonById(
            @Parameter(description = "ID da pessoa", in = ParameterIn.PATH, required = true)
            @PathVariable Long id, @RequestBody @Valid PersonDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deleta uma pessoa pelo ID", description = "DELETE REQUEST para deletar uma pessoa pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Requisição bem sucedida")})
    public ResponseEntity<Boolean> deletePersonById(@Parameter(description = "ID da pessoa", in = ParameterIn.PATH, required = true)
                                                    @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }

    @PostMapping("/addAddress/{idPerson}")
    @Operation(summary = "Adiciona um novo endereço na lista de endereços da pessoa",
            description = "POST REQUEST para adicionar um novo endereço a pessoa pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Requisição bem sucedida")})
    public ResponseEntity<Boolean> addAddressAtPerson(
            @Parameter(description = "ID da pessoa", in = ParameterIn.PATH, required = true)
            @PathVariable Long idPerson, @RequestBody @Valid AddressDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addAddress(idPerson, dto));
    }

    @DeleteMapping("/removeAddress/{idPerson}/{idAddress}")
    @Operation(summary = "Remove um endereço da lista de endereços da pessoa",
            description = "DELETE REQUEST para remover um endereço pelo ID de uma pessoa pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Requisição bem sucedida")})
    public ResponseEntity<Boolean> removeAddressAtPerson(
            @Parameter(description = "ID da pessoa", in = ParameterIn.PATH, required = true)
            @PathVariable Long idPerson,
            @Parameter(description = "ID do endereço", in = ParameterIn.PATH, required = true)
            @PathVariable Long idAddress) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.removeAddress(idPerson, idAddress));
    }

    @PutMapping("/setMainAddress/{idPerson}/{idAddress}")
    @Operation(summary = "Define um endereço como principal de uma pessoa",
            description = "PUT REQUEST para definir o endereço principal de uma pessoa pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Requisição bem sucedida")})
    public ResponseEntity<AddressDTO> setMainAddressAtPerson(
            @Parameter(description = "ID da pessoa", in = ParameterIn.PATH, required = true)
            @PathVariable Long idPerson,
            @Parameter(description = "ID do endereço", in = ParameterIn.PATH, required = true)
            @PathVariable Long idAddress) {
        service.setMainAddress(idPerson, idAddress);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getMainAddress/{idPerson}")
    @Operation(summary = "Recupera o endereço principal de uma pessoa",
            description = "PUT REQUEST para recuperar o endereço principal de uma pessoa pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Requisição bem sucedida")})
    public ResponseEntity<AddressDTO> getMainAddressById(
            @Parameter(description = "ID da pessoa", in = ParameterIn.PATH, required = true)
            @PathVariable Long idPerson) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getMainAddress(idPerson));
    }
}
