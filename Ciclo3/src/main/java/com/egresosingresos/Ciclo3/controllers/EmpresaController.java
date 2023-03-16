package com.egresosingresos.Ciclo3.controllers;

import com.egresosingresos.Ciclo3.models.Empresa;
import com.egresosingresos.Ciclo3.repositories.MovimientosRepository;
import com.egresosingresos.Ciclo3.services.EmpleadoService;
import com.egresosingresos.Ciclo3.services.EmpresaService;
import com.egresosingresos.Ciclo3.services.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
public class EmpresaController {
    @Autowired             //Conexión entre el controlador y el servicio de empresa
    EmpresaService empresaService;

    @GetMapping ({"/","/VerEmpresas"})
    public String viewEmpresas(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empresa> listaEmpresas=empresaService.getAllEmpresas();
        model.addAttribute("emplist", listaEmpresas);
        model.addAttribute("mensaje", mensaje);
        return "verEmpresas"; //Llamamos al HTML
    }

    @GetMapping ("/AgregarEmpresa")
    public String newEmpresa(Model model, @ModelAttribute("mensaje") String mensaje){
        Empresa emp = new Empresa();
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje", mensaje);
        return "agregarEmpresa";
    }

    @PostMapping ("/GuardarEmpresa")
    public String saveEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaService.saveOrUpdateEmpresa(emp)){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarEmpresa";
    }

    @GetMapping ("/EditarEmpresa/{id}")
    public String editEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empresa emp = empresaService.getEmpresaById(id);
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }

    @PostMapping ("/ActualizarEmpresa")
    public String updateEmpresa(@ModelAttribute("emp") Empresa emp, RedirectAttributes redirectAttributes) {
        if (empresaService.saveOrUpdateEmpresa(emp)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect: /EditarEmpresa"+emp.getId();
    }

    @GetMapping ("/EliminarEmpresa/{id}")
    public String deleteEmpresa(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (empresaService.deleteEmpresa(id)) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/VerEmpresas";
        }
            redirectAttributes.addFlashAttribute("mensaje","deleteError");
            return "redirect:/VerEmpresas";
        }
}
