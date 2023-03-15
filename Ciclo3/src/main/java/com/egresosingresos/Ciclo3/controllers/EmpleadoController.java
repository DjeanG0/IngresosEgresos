package com.egresosingresos.Ciclo3.controllers;


import com.egresosingresos.Ciclo3.models.Empleado;
import com.egresosingresos.Ciclo3.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/VerEmpleados")
    public String viewEmpleados (Model model, @ModelAttribute ("mensaje") String mensaje){
        List <Empleado> listaEmpleados=empleadoService.getAllEmpleados();
        model.addAttribute("empleadoList", listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "verEmpleados";
    }
}
