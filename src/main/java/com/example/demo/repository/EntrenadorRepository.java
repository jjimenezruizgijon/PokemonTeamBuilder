package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entity.Entrenador;

// Interfaz de acceso a datos para la entidad Entrenador
// Extiende CrudRepository para obtener operaciones básicas (guardar, buscar, eliminar)
public interface EntrenadorRepository extends CrudRepository<Entrenador, Long> {

    // Método derivado para buscar un entrenador específico por su nombre de usuario
    // Utilizado principalmente durante el proceso de inicio de sesión
    Entrenador findByNombreUsuario(String nombreUsuario);
}