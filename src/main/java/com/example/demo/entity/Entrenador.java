package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

// Define la clase como una entidad que se mapeará a una tabla en la base de datos
@Entity
public class Entrenador {

    // Clave primaria de la entidad, autogenerada por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Credenciales de acceso
    private String nombreUsuario;
    private String password;

    // Un entrenador puede crear múltiples equipos
    // "mappedBy" indica que la relación es gestionada por el campo 'entrenador' en la clase Equipo
    @OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL)
    private List<Equipo> equipos;

    // Constructor vacío para la instanciación por parte de JPA
    public Entrenador() {
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
}