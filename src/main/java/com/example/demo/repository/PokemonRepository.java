package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entity.Pokemon;

// Interfaz de acceso a datos para la entidad Pokemon
// Al extender CrudRepository, hereda automáticamente los métodos necesarios para la gestión de datos
public interface PokemonRepository extends CrudRepository<Pokemon, Long> {
    // No se requieren métodos personalizados; se utilizan las operaciones estándar del framework
}