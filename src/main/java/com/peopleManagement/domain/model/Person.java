package com.peopleManagement.domain.model;

import com.peopleManagement.exceptions.AddressAlreadyExistsException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "TB_PERSON")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private LocalDate dateBirth;

    private Long idMainAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Address> addresses;

    public boolean addAddress(Address address) {
        if (addresses.contains(address)) throw new AddressAlreadyExistsException("Address already exists.");
        if (addresses.isEmpty()) idMainAddress = address.getId();
        return addresses.add(address);
    }

    public boolean removeAddress(Long idAddress) {
        return addresses.removeIf(a -> a.getId().equals(idAddress));
    }
}
