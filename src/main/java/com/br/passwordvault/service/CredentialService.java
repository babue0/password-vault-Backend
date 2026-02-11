package com.br.passwordvault.service;

import com.br.passwordvault.entity.Credential;
import com.br.passwordvault.entity.User;
import com.br.passwordvault.repository.CredentialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

  private final CredentialRepository repository;

  public CredentialService(CredentialRepository repository) {
    this.repository = repository;
  }

  public void addCredential(User user, Credential credential){
    credential.setUser(user);

    repository.save(credential);
  }

  public List<Credential> getCredentials(User user){
    return repository.findByUser(user);
  }

  public void deleteCredential(Long id){
    repository.deleteById(id);
  }
}
