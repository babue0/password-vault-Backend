package com.br.passwordvault.controller;


import com.br.passwordvault.entity.Credential;
import com.br.passwordvault.entity.User;
import com.br.passwordvault.repository.UserRepository;
import com.br.passwordvault.service.CredentialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credentials")
public class CredentialController {

  private final CredentialService credentialService;
  private final UserRepository userRepository;

  public CredentialController(CredentialService credentialService, UserRepository userRepository) {
    this.credentialService = credentialService;
    this.userRepository = userRepository;
  }

  @GetMapping("/{userId}")
  public List<Credential> getAll(@PathVariable Long userId) {
    User user = userRepository.findById(userId).orElseThrow();
    return credentialService.getCredentials(user);
  }

  @PostMapping("/{userId}")
  public Credential add (@PathVariable Long userId, @RequestBody Credential credential) {
    User user = userRepository.findById(userId).orElseThrow();
    credentialService.addCredential(user, credential);
    return credential;
  }


  @PutMapping("/{id}")
  public Credential update (@PathVariable Long id, @RequestBody Credential updatedCredential) {
    return credentialService.getCredentials(null).stream().filter(c -> c.getId().equals(id)).findFirst().map(c -> {
      c.setServiceName(updatedCredential.getServiceName());
      c.setUsername(updatedCredential.getUsername());
      c.setPassword(updatedCredential.getPassword());
      credentialService.addCredential(c.getUser(), c);
      return c;
    }).orElseThrow(() -> new RuntimeException("Credential not found"));
  }

  @DeleteMapping("/{id}")
  public void delete (@PathVariable Long id){
    credentialService.deleteCredential(id);
  }
}
