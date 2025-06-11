package com.Liter.Alura.model;

import jakarta.persistence.*;

@Entity
@Table(name="livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String idioma;
    private Integer numeroDow;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;


    public Livro(){

    }

    public Livro(DadosLivro dadoslivro) {
        this.titulo = dadoslivro.titulo();

        if (dadoslivro.idiomas() != null && !dadoslivro.idiomas().isEmpty()) {
            this.idioma = dadoslivro.idiomas().get(0);
        }

        this.numeroDow = dadoslivro.numeroDow();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDow() {
        return numeroDow;
    }

    public void setNumeroDow(Integer numeroDow) {
        this.numeroDow = numeroDow;
    }


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


    @Override
    public String toString() {
        return "-------------- Livro ----------\n"
                + "Titulo:" + titulo + "\n"
                + "Idioma:" + idioma + "\n"
                + "Número de Downloands:" + numeroDow + "\n"
                + "----------------------------------";
    }



}
