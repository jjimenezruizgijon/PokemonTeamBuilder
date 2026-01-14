package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo.entity.Pokemon;
import com.example.demo.repository.PokemonRepository;

// Clase de configuración para la carga inicial de datos
@Configuration
public class CargaDatos {

    // Método que se ejecuta al iniciar la aplicación
    @Bean
    public CommandLineRunner iniciarDatos(PokemonRepository pokemonRepo) {
        return args -> {
            // Verifica si la base de datos está vacía para evitar duplicados
            if (pokemonRepo.count() == 0) {
                
                // Array con los nombres de los 151 Pokémon
                String[] nombres = {
                    "Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon", "Charizard", "Squirtle", "Wartortle", "Blastoise", "Caterpie", 
                    "Metapod", "Butterfree", "Weedle", "Kakuna", "Beedrill", "Pidgey", "Pidgeotto", "Pidgeot", "Rattata", "Raticate", 
                    "Spearow", "Fearow", "Ekans", "Arbok", "Pikachu", "Raichu", "Sandshrew", "Sandslash", "Nidoran♀", "Nidorina", 
                    "Nidoqueen", "Nidoran♂", "Nidorino", "Nidoking", "Clefairy", "Clefable", "Vulpix", "Ninetales", "Jigglypuff", "Wigglytuff", 
                    "Zubat", "Golbat", "Oddish", "Gloom", "Vileplume", "Paras", "Parasect", "Venonat", "Venomoth", "Diglett", 
                    "Dugtrio", "Meowth", "Persian", "Psyduck", "Golduck", "Mankey", "Primeape", "Growlithe", "Arcanine", "Poliwag", 
                    "Poliwhirl", "Poliwrath", "Abra", "Kadabra", "Alakazam", "Machop", "Machoke", "Machamp", "Bellsprout", "Weepinbell", 
                    "Victreebel", "Tentacool", "Tentacruel", "Geodude", "Graveler", "Golem", "Ponyta", "Rapidash", "Slowpoke", "Slowbro", 
                    "Magnemite", "Magneton", "Farfetch'd", "Doduo", "Dodrio", "Seel", "Dewgong", "Grimer", "Muk", "Shellder", 
                    "Cloyster", "Gastly", "Haunter", "Gengar", "Onix", "Drowzee", "Hypno", "Krabby", "Kingler", "Voltorb", 
                    "Electrode", "Exeggcute", "Exeggutor", "Cubone", "Marowak", "Hitmonlee", "Hitmonchan", "Lickitung", "Koffing", "Weezing", 
                    "Rhyhorn", "Rhydon", "Chansey", "Tangela", "Kangaskhan", "Horsea", "Seadra", "Goldeen", "Seaking", "Staryu", 
                    "Starmie", "Mr. Mime", "Scyther", "Jynx", "Electabuzz", "Magmar", "Pinsir", "Tauros", "Magikarp", "Gyarados", 
                    "Lapras", "Ditto", "Eevee", "Vaporeon", "Jolteon", "Flareon", "Porygon", "Omanyte", "Omastar", "Kabuto", 
                    "Kabutops", "Aerodactyl", "Snorlax", "Articuno", "Zapdos", "Moltres", "Dratini", "Dragonair", "Dragonite", "Mewtwo", "Mew"
                };

                // Array con los tipos principales
                String[] tipos1 = {
                    "Planta", "Planta", "Planta", "Fuego", "Fuego", "Fuego", "Agua", "Agua", "Agua", "Bicho", 
                    "Bicho", "Bicho", "Bicho", "Bicho", "Bicho", "Normal", "Normal", "Normal", "Normal", "Normal", 
                    "Normal", "Normal", "Veneno", "Veneno", "Electrico", "Electrico", "Tierra", "Tierra", "Veneno", "Veneno", 
                    "Veneno", "Veneno", "Veneno", "Veneno", "Hada", "Hada", "Fuego", "Fuego", "Normal", "Normal", 
                    "Veneno", "Veneno", "Planta", "Planta", "Planta", "Bicho", "Bicho", "Bicho", "Bicho", "Tierra", 
                    "Tierra", "Normal", "Normal", "Agua", "Agua", "Lucha", "Lucha", "Fuego", "Fuego", "Agua", 
                    "Agua", "Agua", "Psiquico", "Psiquico", "Psiquico", "Lucha", "Lucha", "Lucha", "Planta", "Planta", 
                    "Planta", "Agua", "Agua", "Roca", "Roca", "Roca", "Fuego", "Fuego", "Agua", "Agua", 
                    "Electrico", "Electrico", "Normal", "Normal", "Normal", "Agua", "Agua", "Veneno", "Veneno", "Agua", 
                    "Agua", "Fantasma", "Fantasma", "Fantasma", "Roca", "Psiquico", "Psiquico", "Agua", "Agua", "Electrico", 
                    "Electrico", "Planta", "Planta", "Tierra", "Tierra", "Lucha", "Lucha", "Normal", "Veneno", "Veneno", 
                    "Tierra", "Tierra", "Normal", "Planta", "Normal", "Agua", "Agua", "Agua", "Agua", "Agua", 
                    "Agua", "Psiquico", "Bicho", "Hielo", "Electrico", "Fuego", "Bicho", "Normal", "Agua", "Agua", 
                    "Agua", "Normal", "Normal", "Agua", "Electrico", "Fuego", "Normal", "Roca", "Roca", "Roca", 
                    "Roca", "Roca", "Normal", "Hielo", "Electrico", "Fuego", "Dragon", "Dragon", "Dragon", "Psiquico", "Psiquico"
                };

                // Array con los tipos secundarios
                String[] tipos2 = {
                    "Veneno", "Veneno", "Veneno", null, null, "Volador", null, null, null, null, 
                    null, "Volador", "Veneno", "Veneno", "Veneno", "Volador", "Volador", "Volador", null, null, 
                    "Volador", "Volador", null, null, null, null, null, null, null, null, 
                    "Tierra", null, null, "Tierra", null, null, null, null, "Hada", "Hada", 
                    "Volador", "Volador", "Veneno", "Veneno", "Veneno", "Planta", "Planta", "Veneno", "Veneno", null, 
                    null, null, null, null, null, null, null, null, null, null, 
                    null, "Lucha", null, null, null, null, null, null, "Veneno", "Veneno", 
                    "Veneno", "Veneno", "Veneno", "Tierra", "Tierra", "Tierra", null, null, "Psiquico", "Psiquico", 
                    "Acero", "Acero", "Volador", "Volador", "Volador", null, "Hielo", null, null, null, 
                    "Hielo", "Veneno", "Veneno", "Veneno", "Tierra", null, null, null, null, null, 
                    null, "Psiquico", "Psiquico", null, null, null, null, null, null, null, 
                    "Roca", "Roca", null, null, null, null, null, null, null, null, 
                    "Psiquico", "Hada", "Volador", "Psiquico", null, null, null, null, null, "Volador", 
                    "Hielo", null, null, null, null, null, null, "Agua", "Agua", "Agua", 
                    "Agua", "Volador", null, "Volador", "Volador", "Volador", null, null, "Volador", null, null
                };

                // Bucle para crear y guardar las entidades
                for (int i = 0; i < nombres.length; i++) {
                    Pokemon p = new Pokemon();
                    p.setNombre(nombres[i]);
                    p.setTipo1(tipos1[i]);
                    p.setTipo2(tipos2[i]);
                    
                    // Generación dinámica de la URL de la imagen
                    p.setImagenUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + (i + 1) + ".png");
                    
                    // Guardado en el repositorio
                    pokemonRepo.save(p);
                }
                
                System.out.println("Carga de datos completada.");
            }
        };
    }
}