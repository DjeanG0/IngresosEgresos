package com.egresosingresos.Ciclo3.repositories;

import com.egresosingresos.Ciclo3.models.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientosRepository extends JpaRepository <MovimientoDinero, Integer> {
}
