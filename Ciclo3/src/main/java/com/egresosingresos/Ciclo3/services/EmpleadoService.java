package com.egresosingresos.Ciclo3.services;

import com.egresosingresos.Ciclo3.models.Empleado;
import com.egresosingresos.Ciclo3.models.Empresa;
import com.egresosingresos.Ciclo3.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<Empleado> getAllEmpleados(){
        List<Empleado> empleadoList = new ArrayList<>();
        empleadoRepository.findAll().forEach(empleado -> empleadoList.add(empleado));
        return empleadoList;
    }

    //Metodo para buscar empleados por id
    public Optional<Empleado> getEmpleadoById(Integer id){
        return empleadoRepository.findById(id);
    }

    public boolean saveOrUpdateEmpleado(Empleado empl){
        Empleado emp = empleadoRepository.save(empl);
        if(empleadoRepository.findById(emp.getId())!=null){
            return true;
        }
        return false;
    }

    //Metodo para eliminar un registro de empleado por id
    public boolean deleteEmpleado(Integer id){
        empleadoRepository.deleteById(id);
        if(this.empleadoRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    //Metodo para buscar empleados por empresa
    public ArrayList<Empleado> obtenerPorEmpresa(Integer id){
        return empleadoRepository.findByEmpresa(id);
    }

}
