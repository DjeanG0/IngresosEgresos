package com.egresosingresos.Ciclo3.services;

import com.egresosingresos.Ciclo3.models.Empresa;
import com.egresosingresos.Ciclo3.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {
    @Autowired  //Conecta esta clase con el repositorio de empresa
    EmpresaRepository empresaRepository;

    //Método que retorna la lista de empresas usando metodos heredados de JpaRepository
    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresaList = new ArrayList<>();
        empresaRepository.findAll().forEach(empresa -> empresaList.add(empresa)); //Se recorre el objeto iterable y se buscan las empresas para ponerlas en la lista de empresas
        return empresaList;
    }

    //Método que trae un objeto de tipo empresa por el id
    public Empresa getEmpresaById(Integer id){
        return empresaRepository.findById(id).get();
    }

    //Método para guardar o actualizar objetos de tipo empresa
    public boolean saveOrUpdateEmpresa(Empresa empresa){
        Empresa emp= empresaRepository.save(empresa);
        if (empresaRepository.findById(emp.getId())!=null){
            return true;
        }
        return false;
    }

    //Método delete para eliminar empresas registradas
    public boolean deleteEmpresa(Integer id){
        empresaRepository.deleteById(id);
        if (empresaRepository.findById(id)!=null) {   //Verifica si la empresa aún existe
            return true;
        }
        return false;
    }

}
