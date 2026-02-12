package com.br.passwordvault.controller;


import com.br.passwordvault.entity.User;
import com.br.passwordvault.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    try{
      return ResponseEntity.ok(userService.registerUser(user));
    } catch (Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
    try{
      return ResponseEntity.ok(userService.login(user.getUsername(), user.getMasterPassword()));
    } catch (Exception e){
      return ResponseEntity.status(401).body(e.getMessage());
    }
  }
}
