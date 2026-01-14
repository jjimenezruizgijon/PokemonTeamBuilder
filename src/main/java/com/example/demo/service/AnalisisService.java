package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Equipo;
import com.example.demo.entity.Pokemon;

@Service
public class AnalisisService {

    // Mapa estático con las debilidades principales
    private static final Map<String, List<String>> TABLA_TIPOS = new HashMap<>();

    static {
        // ¿Quién le hace daño a quién? 
        TABLA_TIPOS.put("Fuego", List.of("Agua", "Roca", "Tierra"));
        TABLA_TIPOS.put("Agua", List.of("Electrico", "Planta"));
        TABLA_TIPOS.put("Planta", List.of("Fuego", "Hielo", "Veneno", "Volador", "Bicho"));
        TABLA_TIPOS.put("Electrico", List.of("Tierra"));
        TABLA_TIPOS.put("Roca", List.of("Agua", "Planta", "Lucha", "Tierra", "Acero"));
        TABLA_TIPOS.put("Psiquico", List.of("Bicho", "Fantasma", "Siniestro"));
        TABLA_TIPOS.put("Fantasma", List.of("Fantasma", "Siniestro"));
        TABLA_TIPOS.put("Siniestro", List.of("Lucha", "Bicho", "Hada"));
        TABLA_TIPOS.put("Dragon", List.of("Hielo", "Dragon", "Hada"));
        TABLA_TIPOS.put("Volador", List.of("Electrico", "Hielo", "Roca"));
        TABLA_TIPOS.put("Veneno", List.of("Tierra", "Psiquico"));
        TABLA_TIPOS.put("Lucha", List.of("Volador", "Psiquico", "Hada"));
        TABLA_TIPOS.put("Normal", List.of("Lucha"));

    }

    public List<String> analizarDebilidades(Equipo equipo) {
        // Mapa para contar cuántos pokemon del equipo sufren contra cada tipo
        Map<String, Integer> contadorDebilidades = new HashMap<>();

        for (Pokemon p : equipo.getPokemons()) {
            registrarDebilidad(contadorDebilidades, p.getTipo1());
            if (p.getTipo2() != null) {
                registrarDebilidad(contadorDebilidades, p.getTipo2());
            }
        }

        // Solo avisamos si 3 o más pokemon son débiles al mismo tipo
        List<String> avisos = new ArrayList<>();
        contadorDebilidades.forEach((tipoAtacante, cantidad) -> {
            if (cantidad >= 3) {
                avisos.add("⚠️ ¡Cuidado! Tu equipo es muy débil a: " + tipoAtacante + " (" + cantidad + " Pokémon sufren).");
            } else if (cantidad == 2) {
                 // Opcional: Avisos leves
                 // avisos.add("Ojo con el tipo " + tipoAtacante);
            }
        });

        if (avisos.isEmpty()) {
            avisos.add("✅ ¡Equipo equilibrado! No tienes debilidades masivas.");
        }

        return avisos;
    }

    private void registrarDebilidad(Map<String, Integer> contador, String tipoDefensor) {
        if (TABLA_TIPOS.containsKey(tipoDefensor)) {
            List<String> atacantes = TABLA_TIPOS.get(tipoDefensor);
            for (String atacante : atacantes) {
                // Sumamos 1 al contador de ese tipo atacante
                contador.put(atacante, contador.getOrDefault(atacante, 0) + 1);
            }
        }
    }
}