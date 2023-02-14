package com.softuni.battleshipsapp.repositories;

import com.softuni.battleshipsapp.models.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipRepository extends JpaRepository<Ship, Long> {

    Optional<Ship> findByName(String name);
}
