package com.egresosingresos.Ciclo3.controllers;


import com.egresosingresos.Ciclo3.models.Empleado;
import com.egresosingresos.Ciclo3.models.Empresa;
import com.egresosingresos.Ciclo3.services.EmpleadoService;
import com.egresosingresos.Ciclo3.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Controller
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    EmpresaService empresaService;
    @Autowired
    MovimientosController movimientosController;

    @GetMapping("/VerEmpleados")
    public String viewEmpleados (Model model, @ModelAttribute ("mensaje") String mensaje){
        List <Empleado> listaEmpleados=empleadoService.getAllEmpleados();
        model.addAttribute("empleList", listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "verEmpleados";
    }

    @GetMapping("/AgregarEmpleado")
    public String newEmpleado(Model model, @ModelAttribute("mensaje") String mensaje){
        Empleado empl = new Empleado();
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listaEmpresas = empresaService.getAllEmpresas();
        model.addAttribute("emprelist", listaEmpresas);
        return "agregarEmpleado";
    }

    @PostMapping("/GuardarEmpleado")
    public String saveEmpleado(Empleado empl, RedirectAttributes redirectAttributes){
        String passEncriptada = passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);
        if(empleadoService.saveOrUpdateEmpleado(empl)){
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AgregarEmpleado";
    }

    @GetMapping("/EditarEmpleado/{id}")
    public String editEmpleado(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empleado empl = empleadoService.getEmpleadoById(id).get();
        model.addAttribute("empl", empl);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listaEmpresas = empresaService.getAllEmpresas();
        model.addAttribute("emprelist", listaEmpresas);
        return "editarEmpleado";
    }

    @PostMapping("/ActualizarEmpleado")
    public String updateEmpleado(@ModelAttribute("empl") Empleado empl, RedirectAttributes redirectAttributes){
        Integer id = empl.getId(); //Obtener el id del objeto empleado
        String Oldpass = empleadoService.getEmpleadoById(id).get().getPassword(); //Con ese Id consultamos si password esta en DB
        if(!empl.getPassword().equals(Oldpass)){
            String passEncriptada = passwordEncoder().encode(empl.getPassword());
            empl.setPassword(passEncriptada);
        }
        if(empleadoService.saveOrUpdateEmpleado(empl)){
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarEmpleado"+empl.getId();
    }

    @GetMapping("/EliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if(empleadoService.deleteEmpleado(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/VerEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerEmpleados";
    }

    @GetMapping("/Empresa/{id}/Empleados") //Filtrar los empleados por empresa
    public String verEmpleadosPorEmpresa(@PathVariable("id") Integer id, Model model){
        List<Empleado> listaEmpleados = empleadoService.obtenerPorEmpresa(id);
        model.addAttribute("emplelist", listaEmpleados);
        return "verEmpleados";
    }

    //Metodo para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
