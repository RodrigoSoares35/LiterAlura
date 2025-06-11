package com.Liter.Alura.repository;

import com.Liter.Alura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Query("SELECT l FROM Livro l JOIN FETCH l.autor WHERE l.idioma = :idioma ORDER BY l.titulo ASC")
    List<Livro> findByIdioma(@Param("idioma") String idioma);

}
