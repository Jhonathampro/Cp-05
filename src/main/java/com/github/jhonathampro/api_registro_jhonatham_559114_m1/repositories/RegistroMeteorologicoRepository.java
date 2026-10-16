package com.github.jhonathampro.api_registro_jhonatham_559114_m1.repositories;

import com.github.jhonathampro.api_registro_jhonatham_559114_m1.entities.RegistroMeteorologico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroMeteorologicoRepository extends JpaRepository<RegistroMeteorologico, Long> {
}
