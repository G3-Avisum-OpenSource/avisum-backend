package pe.edu.upc.avisum.shared.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Recursos reservados para siguientes sprints (sensores, conteo de pasajeros). Responden una lista vacía. */
@RestController
@RequestMapping("/api/v1")
public class PlaceholderController {

    @GetMapping({"/drivers", "/sensors", "/passenger-counts"})
    public List<Object> empty() {
        return List.of();
    }
}