package com.softuni.battleshipsapp.repositories;

import com.softuni.battleshipsapp.models.Category;
import com.softuni.battleshipsapp.models.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByName(ShipType name);
}
