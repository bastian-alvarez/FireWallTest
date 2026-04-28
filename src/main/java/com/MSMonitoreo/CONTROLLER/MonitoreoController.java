package com.MSMonitoreo.CONTROLLER;

import java.util.List;
import com.MSMonitoreo.DTO.CoordenadaRequest;
import com.MSMonitoreo.MODEL.Reporte;
import com.MSMonitoreo.SERVICES.MonitoreoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monitoreo")
public class MonitoreoController {

    private final MonitoreoService service;

    public MonitoreoController(MonitoreoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reporte> obtenerReportes() {
        return service.obtenerReportes();
    }

    @PostMapping
    public Reporte crearReporte(@RequestBody CoordenadaRequest datos) {
        return service.procesarDatos(
            datos.getLatitud(),
            datos.getLongitud(),
            datos.getDescripcion()
        );
    }
}