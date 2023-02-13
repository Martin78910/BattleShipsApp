package com.softuni.battleshipsapp.services;

import com.softuni.battleshipsapp.models.User;
import com.softuni.battleshipsapp.models.dtos.LoginDTO;
import com.softuni.battleshipsapp.models.dtos.UserRegistrationDTO;
import com.softuni.battleshipsapp.repositories.UserRepository;
import com.softuni.battleshipsapp.session.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    private final UserRepository userRepository;
    private final LoggedUser userSession;

    @Autowired
    public AuthService(UserRepository userRepository, LoggedUser userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }



    public boolean register(UserRegistrationDTO registrationDTO){
        if(!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
            return false;
        }
        //duplicate email

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());
        if(byEmail.isPresent()){
            return false;
        }

        //duplicate username

        Optional<User> byUsername = this.userRepository.findByUsername(registrationDTO.getUsername());
        if(byUsername.isPresent()){
            return false;
        }


        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setFullName(registrationDTO.getFullName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        this.userRepository.save(user);
        return true;
    }

    public boolean login(LoginDTO loginDTO) {
//        return false;

        Optional<User> user = this.userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        if(user.isEmpty()){
            return false;
        }

        //actual login
        this.userSession.login(user.get());

        return true;
    }
}
