package com.MSMonitoreo.SERVICES;

import java.util.List;
import com.MSMonitoreo.MODEL.Coordenada;
import com.MSMonitoreo.MODEL.Reporte;
import com.MSMonitoreo.REPOSITORY.CoordenadaRepository;
import com.MSMonitoreo.REPOSITORY.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MonitoreoService {

    private final CoordenadaRepository coordenadaRepository;
    private final ReporteRepository reporteRepository;

    public MonitoreoService(CoordenadaRepository coordenadaRepository,
                            ReporteRepository reporteRepository) {
        this.coordenadaRepository = coordenadaRepository;
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> obtenerReportes() {
        return reporteRepository.findAll();
    }

    public Reporte procesarDatos(Double lat, Double lon, String descripcion) {

        Coordenada coord = new Coordenada();
        coord.setLatitud(lat);
        coord.setLongitud(lon);
        coord.setDescripcion(descripcion);
        coord.setFecha(LocalDateTime.now());

        coordenadaRepository.save(coord);

        String url = "https://www.google.com/maps?q=" + lat + "," + lon;

        Reporte reporte = new Reporte();
        reporte.setGoogleMapsUrl(url);
        reporte.setCoordenada(coord);
        reporte.setFechaGeneracion(LocalDateTime.now());

        return reporteRepository.save(reporte);
    }
}