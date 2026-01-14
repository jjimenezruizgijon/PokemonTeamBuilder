package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.entity.Entrenador;
import com.example.demo.entity.Equipo;
import com.example.demo.entity.Pokemon;
import com.example.demo.repository.EntrenadorRepository;
import com.example.demo.repository.EquipoRepository;
import com.example.demo.repository.PokemonRepository;
import com.example.demo.service.AnalisisService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PokemonController {

    // Inyección de repositorios para acceso a base de datos
    @Autowired
    private PokemonRepository pokemonRepo;
    @Autowired
    private EquipoRepository equipoRepo;
    @Autowired
    private EntrenadorRepository entrenadorRepo;
    
    // Inyección del servicio de lógica de negocio
    @Autowired
    private AnalisisService analisisService;

    // --- LOGIN Y SESIONES ---

    // Muestra la pantalla inicial de acceso
    @GetMapping("/")
    public String paginaLogin() {
        return "login";
    }

    // Procesa el formulario de entrada o registro
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        // Busca si el usuario ya existe
        Entrenador entrenador = entrenadorRepo.findByNombreUsuario(username);

        if (entrenador != null) {
            // Usuario existente: verifica la contraseña
            if (entrenador.getPassword().equals(password)) {
                session.setAttribute("entrenadorLogueado", entrenador);
                return "redirect:/generaciones"; 
            } else {
                model.addAttribute("error", "Contraseña incorrecta");
                return "login";
            }
        } else {
            // Usuario nuevo: registro automático
            Entrenador nuevo = new Entrenador();
            nuevo.setNombreUsuario(username);
            nuevo.setPassword(password);
            entrenadorRepo.save(nuevo);
            
            session.setAttribute("entrenadorLogueado", nuevo);
            return "redirect:/generaciones";
        }
    }

    // Cierra la sesión del usuario actual
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // --- NAVEGACIÓN ---

    // Muestra la pantalla de selección de generación
    @GetMapping("/generaciones")
    public String verGeneraciones(HttpSession session) {
        // Verificación de seguridad: usuario en sesión requerido
        if (session.getAttribute("entrenadorLogueado") == null) return "redirect:/";
        return "generaciones";
    }

    // Carga el constructor de equipos con los datos de la G 1
    @GetMapping("/builder/gen1")
    public String verBuilderGen1(Model model, HttpSession session) {
        if (session.getAttribute("entrenadorLogueado") == null) return "redirect:/";

        // Obtiene la lista completa de Pokémon y la envía a la vista
        var listaDePokemon = pokemonRepo.findAll();
        model.addAttribute("misPokemons", listaDePokemon);
        
        return "index";
    }
    
    // --- GUARDADO DE DATOS ---

    // Recibe los datos del formulario para crear un equipo nuevo
    @PostMapping("/guardar-equipo")
    public String guardarEquipo(
            @RequestParam String nombreEquipo,
            @RequestParam(required = false) List<Long> idPokemons,
            HttpSession session
    ) {
        Entrenador entrenadorSesion = (Entrenador) session.getAttribute("entrenadorLogueado");
        if (entrenadorSesion == null) return "redirect:/";

        // Validación: el equipo debe tener entre 1 y 6 integrantes
        if (idPokemons == null || idPokemons.isEmpty() || idPokemons.size() > 6) {
            return "redirect:/builder/gen1?error=tamano";
        }

        // Recupera las entidades Pokémon seleccionadas desde la base de datos
        List<Pokemon> pokemonsSeleccionados = (List<Pokemon>) pokemonRepo.findAllById(idPokemons);

        // Crea el objeto Equipo y establece las relaciones
        Equipo equipo = new Equipo();
        equipo.setNombreEquipo(nombreEquipo);
        equipo.setEntrenador(entrenadorSesion); 
        equipo.setPokemons(pokemonsSeleccionados);

        // Guarda el equipo en la base de datos
        equipoRepo.save(equipo);
        
        return "redirect:/mis-equipos";
    }

    // --- CONSULTA Y ANÁLISIS ---
    
    // Muestra los equipos del usuario y su análisis de tipos
    @GetMapping("/mis-equipos")
    public String verMisEquipos(Model model, HttpSession session) {
        Entrenador entrenador = (Entrenador) session.getAttribute("entrenadorLogueado");
        if (entrenador == null) return "redirect:/";

        // Filtra los equipos pertenecientes al usuario actual
        List<Equipo> susEquipos = equipoRepo.findByEntrenador(entrenador);
        model.addAttribute("misEquipos", susEquipos);

        // Genera el análisis de debilidades para cada equipo
        java.util.Map<Long, List<String>> analisisPorEquipo = new java.util.HashMap<>();
        
        for (Equipo e : susEquipos) {
            List<String> analisis = analisisService.analizarDebilidades(e);
            analisisPorEquipo.put(e.getId(), analisis);
        }
        
        model.addAttribute("analisisMap", analisisPorEquipo);
        
        return "mis-equipos";
    }
    
    // --- ELIMINACIÓN ---

    // Elimina un equipo específico por su ID
    @GetMapping("/borrar-equipo")
    public String borrarEquipo(@RequestParam Long id) {
        equipoRepo.deleteById(id);
        return "redirect:/mis-equipos";
    }
}