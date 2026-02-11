package com.br.passwordvault.repository;

import com.br.passwordvault.entity.Credential;
import com.br.passwordvault.entity.User; // Import correto
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

  List<Credential> findByUser(User user);
}
