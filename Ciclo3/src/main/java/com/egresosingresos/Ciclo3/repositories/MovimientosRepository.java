package com.egresosingresos.Ciclo3.repositories;

import com.egresosingresos.Ciclo3.models.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MovimientosRepository extends JpaRepository <MovimientoDinero, Integer> {
    //Metodo para filtrar movimientos por empleado
    @Query(value = "SELECT * FROM movimientos WHERE empleado_id = ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpleado(Integer id);

    //Metodo para filtrar movimientos por empresa
    @Query(value = "SELECT * FROM movimientos WHERE empleado_id in (SELECT id FROM empleado WHERE empresa_id = ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);

    //Metodo para ver la suma de todos los movimientos
    @Query(value="SELECT SUM(monto) FROM movimientos", nativeQuery = true)
    public abstract Long SumarMonto();

    //Metoso para ver la suma de los montos por empleado
    @Query(value = "SELECT SUM(monto) FROM movimientos WHERE empleado_id = ?1", nativeQuery = true)
    public abstract Long MontosPorEmpleado(Integer id);  // id del empleado

    //Metodo para ver la suma de los movimientos por empresa
    @Query(value = "SELECT SUM(monto) FROM movimientos WHERE empleado_id IN (SELECT id FROM empleado WHERE empresa_id = ?1)", nativeQuery = true)
    public abstract Long MontosPorEmpresa(Integer id);

    //Metodo que trae el id de un usuario cuando tengo su correo
    @Query(value = "SELECT id FROM empleado WHERE correo = ?1", nativeQuery = true)
    public abstract Integer IdPorCorreo(String correo);
}
