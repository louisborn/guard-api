package com.guard.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

    @Query("SELECT o FROM Operator o WHERE o.token=?1")
    Optional<Operator> findOperatorByToken(String token);

    @Query("SELECT o FROM Operator o WHERE o.email=?1")
    Optional<Operator> findOperatorByEmail(String email);
}