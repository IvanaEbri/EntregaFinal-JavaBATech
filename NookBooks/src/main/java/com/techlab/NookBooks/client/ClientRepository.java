package com.techlab.NookBooks.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findByActiveTrueAndId (Long Id);
    List<Client> findByActiveTrueAndClientNameContaining (String clientName);
    List<Client> findByActiveTrue ();
}