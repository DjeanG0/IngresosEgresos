package com.egresosingresos.Ciclo3.services;

import com.egresosingresos.Ciclo3.models.Empleado;
import com.egresosingresos.Ciclo3.models.MovimientoDinero;
import com.egresosingresos.Ciclo3.repositories.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientosService {
    @Autowired
    MovimientosRepository movimientosRepository;

    public List<MovimientoDinero> getAllMovimientos(){
        List<MovimientoDinero> movimientosList = new ArrayList<>();
        movimientosRepository.findAll().forEach(movimiento -> movimientosList.add(movimiento));
        return movimientosList;
    }

    public MovimientoDinero getMovimientoById(Integer id){
        return movimientosRepository.findById(id).get();
    }

    public boolean saveOrUpdateMovimiento(MovimientoDinero movimientoDinero){
        MovimientoDinero mov = movimientosRepository.save(movimientoDinero);
        if(movimientosRepository.findById(mov.getId())!=null){
            return true;
        }
        return false;
    }

    public boolean deleteMovimiento(Integer id){
        movimientosRepository.deleteById(id);
        if(this.movimientosRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpleado(Integer id){
        return movimientosRepository.findByEmpleado(id);
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpresa(Integer id){
        return movimientosRepository.findByEmpresa(id);
    }

    public Long obtenerSumaMontos(){
        return movimientosRepository.SumarMonto();
    }

    public Long MontosPorEmpleado(Integer id){
        return movimientosRepository.MontosPorEmpleado(id);
    }

    public Long MontosPorEmpresa(Integer id){
        return movimientosRepository.MontosPorEmpresa(id);
    }

    public Integer IdPorCorreo(String Correo){
        return movimientosRepository.IdPorCorreo(Correo);
    }

}
