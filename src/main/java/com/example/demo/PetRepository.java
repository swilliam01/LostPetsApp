package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
    Iterable<Pet> findAllByStatus(String status);
}
