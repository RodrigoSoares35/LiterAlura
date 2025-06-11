package com.Liter.Alura.repository;

import com.Liter.Alura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Integer > {

    Optional<Autor> findByNomeAutor(String nomeAutor);

    @Query("SELECT DISTINCT l FROM Autor l WHERE l.anoNasc <= :ano AND"
            +"(l.anoMorte IS NULL OR l.anoMorte > :ano) ORDER BY l.nomeAutor ASC")
    List<Autor> findAutoresVivosNoAno(@Param("ano") Integer ano);

    @Query("SELECT a FROM Autor a ORDER BY a.nomeAutor ASC")
    public List<Autor> findAutores();

}
