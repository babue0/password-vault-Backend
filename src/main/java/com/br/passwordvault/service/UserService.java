package com.br.passwordvault.service;


import com.br.passwordvault.entity.User;
import com.br.passwordvault.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(User user){
    if (repository.findByUsername(user.getUsername()).isPresent()){
      throw new RuntimeException("Username already exists");
    }

    String encodedPassword = passwordEncoder.encode(user.getMasterPassword());
    user.setMasterPassword(encodedPassword);

    return repository.save(user);
  }



  public User login(String username, String rawPassword){
    Optional<User> userOptional = repository.findByUsername(username);

    if (userOptional.isEmpty()){
      throw new RuntimeException("user not found");
    }

    User user = userOptional.get();

    if (!passwordEncoder.matches(rawPassword, user.getMasterPassword())){
      throw new RuntimeException("Invalid password");
    }

    return user;
  }
}
