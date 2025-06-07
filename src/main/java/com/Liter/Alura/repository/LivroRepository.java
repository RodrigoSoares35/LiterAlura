package com.Liter.Alura.repository;

import com.Liter.Alura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

//    @Query("SELECT DISTINCT l.nomeAutor FROM Livro l ORDER BY l.nomeAutor ASC")
//    public List<String> findNomeAutor();
//
//    @Query("SELECT DISTINCT l.nomeAutor FROM Livro l WHERE l.anoNasc <= :ano AND"
//            +"(l.anoMorte IS NULL OR l.anoMorte > :ano) ORDER BY l.nomeAutor ASC")
//    List<String> findAutoresVivosNoAno(@Param("ano") Integer ano);
//
//    @Query("SELECT l FROM Livro l WHERE l.idioma = :idioma ORDER BY l.titulo ASC")
//    List<Livro> findByIdioma(@Param("idioma") String idioma);
}
