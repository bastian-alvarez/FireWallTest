package com.firewall.ms_reportes.controller;

import com.firewall.ms_reportes.entity.Reporte;
import com.firewall.ms_reportes.entity.Usuario;
import com.firewall.ms_reportes.repository.ReporteRepository;
import com.firewall.ms_reportes.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/enviar")
    public Reporte crearReporte(@RequestBody Reporte nuevoReporte, HttpServletRequest request) {
        
        // 1. Capturar la IP automáticamente por seguridad
        String ipCliente = request.getRemoteAddr();
        
        // 2. Manejar el Usuario (RUT e IP)
        Usuario user = nuevoReporte.getUsuario();
        user.setDireccionIp(ipCliente);
        
        // Buscamos si ya existe el usuario por RUT, si no, lo guardamos
        Usuario usuarioGuardado = usuarioRepository.findByRut(user.getRut())
                .orElseGet(() -> usuarioRepository.save(user));

        nuevoReporte.setUsuario(usuarioGuardado);

        // 3. Vincular las relaciones hijas con su padre (el Reporte)
        
        // Si el celular envió coordenadas, le decimos de qué reporte son
        if (nuevoReporte.getUbicacion() != null) {
            nuevoReporte.getUbicacion().setReporte(nuevoReporte);
        }
        
        // Si el celular envió fotos/videos, los recorremos y los vinculamos al reporte
        if (nuevoReporte.getMultimedia() != null) {
            nuevoReporte.getMultimedia().forEach(archivo -> archivo.setReporte(nuevoReporte));
        }

        // 4. Guardar TODO (Gracias al CascadeType.ALL, esto guarda en las 4 tablas a la vez)
        return reporteRepository.save(nuevoReporte);
    }
}