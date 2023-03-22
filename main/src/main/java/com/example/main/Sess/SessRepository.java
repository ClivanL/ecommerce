package com.example.main.Sess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessRepository extends JpaRepository<Sess,Long> {
}
