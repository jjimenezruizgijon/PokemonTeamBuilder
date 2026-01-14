package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

// Entidad que representa a un Pokémon en el sistema de persistencia
@Entity
public class Pokemon {

    // Clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos descriptivos del Pokémon
    private String nombre;
    private String tipo1;
    private String tipo2;
    private String imagenUrl; // Almacena la dirección URL de la imagen oficial

    // "mappedBy" indica que la relación es propietaria de la entidad Equipo
    @ManyToMany(mappedBy = "pokemons")
    private List<Equipo> equipos;

    // Constructor por defecto requerido por JPA
    public Pokemon() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
}