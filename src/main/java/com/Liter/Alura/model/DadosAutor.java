package com.Liter.Alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(String name,
                         @JsonAlias("birth_year") Integer anoNasc,
                         @JsonAlias("death_year") Integer anoMorte) {
}
