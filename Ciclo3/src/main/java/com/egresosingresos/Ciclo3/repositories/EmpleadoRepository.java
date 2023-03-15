package com.egresosingresos.Ciclo3.repositories;

import com.egresosingresos.Ciclo3.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository <Empleado, Integer> {
}
