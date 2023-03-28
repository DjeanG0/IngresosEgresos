package com.egresosingresos.Ciclo3.controllers;

import com.egresosingresos.Ciclo3.models.Empleado;
import com.egresosingresos.Ciclo3.models.MovimientoDinero;
import com.egresosingresos.Ciclo3.repositories.MovimientosRepository;
import com.egresosingresos.Ciclo3.services.EmpleadoService;
import com.egresosingresos.Ciclo3.services.EmpresaService;
import com.egresosingresos.Ciclo3.services.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class MovimientosController {

    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    EmpresaService empresaService;
    @Autowired
    MovimientosService movimientosService;
    @Autowired
    MovimientosRepository movimientosRepository;

    @RequestMapping("/VerMovimientos")
    public String viewMovimientos(@RequestParam(value = "pagina", required = false, defaultValue = "1") int NumeroPagina,
        @RequestParam(value = "medida", required = false, defaultValue = "5") int medida,
        Model model, @ModelAttribute("mendaje") String mensaje){
        Page<MovimientoDinero> paginaMovimientos = movimientosRepository.findAll(PageRequest.of(NumeroPagina, medida));
        model.addAttribute("movlist", paginaMovimientos.getContent());
        model.addAttribute("paginas", new int[paginaMovimientos.getTotalPages()]);
        model.addAttribute("paginaActual", NumeroPagina);
        model.addAttribute("mensaje", mensaje);
        Long sumaMonto = movimientosService.obtenerSumaMontos();
        model.addAttribute("SumaMontos", sumaMonto); //Mandamos la suma de todos los montos a la plantilla
        return "verMovimientos";
    }

    @GetMapping("/AgregarMovimientos")
    public String nuevoMovimiento(Model model, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero movimiento = new MovimientoDinero();
        model.addAttribute("mov", movimiento);
        model.addAttribute("mensaje", mensaje);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idEmpleado = movimientosService.IdPorCorreo(correo);
        model.addAttribute("idEmpleado", idEmpleado);
        return "agregarMovimiento";
    }

    @PostMapping("/GuardarMovimiento")
    public String guardarMovimiento(MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosService.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AgregarMovimiento";
    }

    @GetMapping("/EditarMovimiento/{id}")
    public String editarMovimiento(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero mov = movimientosService.getMovimientoById(id);
        model.addAttribute("mov", mov);
        model.addAttribute("mensaje", mensaje);
        List<Empleado> listaEmpleados = empleadoService.getAllEmpleados();
        model.addAttribute("emplelist", listaEmpleados);
        return "editarMovimiento";
    }

    @PostMapping("/ActualizarMovimiento")
    public String updateMovimiento(@ModelAttribute("mov") MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosService.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje", "updteOK");
            return "redirect:/Vermovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarMovimiento/"+mov.getId();
    }

    @GetMapping("/EliminarMovimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if(movimientosService.deleteMovimiento(id)){
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/VerMovimientos";
    }

    @GetMapping("/Empleado/{id}/Movimientos")
    public String movimientosPorEmpleado(@PathVariable("id") Integer id, Model model){
        List<MovimientoDinero> movlist = movimientosService.obtenerPorEmpleado(id);
        model.addAttribute("movlist", movlist);
        Long sumaMonto = movimientosService.MontosPorEmpleado(id);
        model.addAttribute("SumaMontos", sumaMonto);
        return "verMovimientos";
    }

    @GetMapping("/Empresa/{id}/Movimientos")
    public String movimientosPorEmpresa(@PathVariable("id") Integer id, Model model){
        List<MovimientoDinero> movList = movimientosService.obtenerPorEmpresa(id);
        model.addAttribute("movlist", movList);
        Long sumaMonto = movimientosService.MontosPorEmpresa(id);
        model.addAttribute("SumaMontos", sumaMonto);
        return "verMovimientos";
    }

    @RequestMapping(value = "/Denegado")
    public String accesoDenegado(){
        return "accessDenied";
    }

}
