package com.softuni.battleshipsapp.services;

import com.softuni.battleshipsapp.models.Category;
import com.softuni.battleshipsapp.models.Ship;
import com.softuni.battleshipsapp.models.ShipType;
import com.softuni.battleshipsapp.models.User;
import com.softuni.battleshipsapp.models.dtos.CreateShipDTO;
import com.softuni.battleshipsapp.repositories.CategoryRepository;
import com.softuni.battleshipsapp.repositories.ShipRepository;
import com.softuni.battleshipsapp.repositories.UserRepository;
import com.softuni.battleshipsapp.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final CategoryRepository categoryRepository;
    private final LoggedUser loggedUser;
    private final UserRepository userRepository;



    public ShipService(ShipRepository shipRepository, CategoryRepository categoryRepository,
                       LoggedUser loggedUser, UserRepository userRepository) {
        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
    }

    public boolean create(CreateShipDTO createShipDTO) {

        Optional<Ship> byName = this.shipRepository.findByName(createShipDTO.getName());

        if(byName.isPresent()){
            return false;
        }

        ShipType type = switch (createShipDTO.getCategory()){
            case 0 -> ShipType.BATTLE;
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.BATTLE;
        };

        Category category = this.categoryRepository.findByName(type);
        Optional<User> user = this.userRepository.findById(this.loggedUser.getId());
        Ship ship = new Ship();
        ship.setName(createShipDTO.getName());
        ship.setPower(createShipDTO.getPower());
        ship.setHealth(createShipDTO.getHealth());
        ship.setCreated(createShipDTO.getCreated());
        ship.setCategory(category);
        ship.setUser(user.get());

        this.shipRepository.save(ship);
        return true;
    }
}
