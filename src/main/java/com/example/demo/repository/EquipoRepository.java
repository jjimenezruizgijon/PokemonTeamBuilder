package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entity.Equipo;
import java.util.List;
import com.example.demo.entity.Entrenador;

// Interfaz de acceso a datos para la entidad Equipo
public interface EquipoRepository extends CrudRepository<Equipo, Long> {

    // Método personalizado para recuperar una lista de equipos asociados a un entrenador concreto
    // Permite filtrar los equipos en la vista "Mis Equipos"
    List<Equipo> findByEntrenador(Entrenador entrenador);
}