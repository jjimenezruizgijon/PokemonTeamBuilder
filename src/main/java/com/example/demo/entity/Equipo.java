package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

// Entidad que representa la estructura de un equipo en la base de datos
@Entity
public class Equipo {

    // Identificador único (clave primaria) generado automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo para almacenar el nombre del equipo
    private String nombreEquipo;

    // Asocia el equipo a su entrenador creador
    // Se utiliza una clave foránea 'entrenador_id' para vincularlos
    @ManyToOne
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;

    // Define la lista de Pokémon que componen el equipo
    // Configura la tabla intermedia 'equipo_pokemon' para gestionar la relación
    @ManyToMany
    @JoinTable(
        name = "equipo_pokemon",
        joinColumns = @JoinColumn(name = "equipo_id"),
        inverseJoinColumns = @JoinColumn(name = "pokemon_id")
    )
    private List<Pokemon> pokemons;

    // Constructor vacío para el framework de persistencia
    public Equipo() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}