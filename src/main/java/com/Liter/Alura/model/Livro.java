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
    private String  nomeAutor;
    private Integer anoNasc;
    private Integer anoMorte;

    public Livro(){

    }

    public Livro(DadosLivro dadoslivro) {
        this.titulo = dadoslivro.titulo();

        // Pega o primeiro idioma da lista, se houver
        if (dadoslivro.idiomas() != null && !dadoslivro.idiomas().isEmpty()) {
            this.idioma = dadoslivro.idiomas().get(0);
        }

        this.numeroDow = dadoslivro.numeroDow();

        // Pega o primeiro autor da lista, se houver
        if (dadoslivro.autor() != null && !dadoslivro.autor().isEmpty()) {
            this.nomeAutor = dadoslivro.autor().get(0).name();
            this.anoNasc = dadoslivro.autor().get(0).anoNasc();
            this.anoMorte = dadoslivro.autor().get(0).anoMorte();
        }
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

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getAnoNasc() {
        return anoNasc;
    }

    public void setAnoNasc(Integer anoNasc) {
        this.anoNasc = anoNasc;
    }

    public Integer getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }

    @Override
    public String toString() {
        return "-------------- Livro ----------\n"
                + "Id:" + id + "\n"
                + "Titulo:" + titulo + "\n"
                + "Idioma:" + idioma + "\n"
                + "Numero de Downlond:" + numeroDow + "\n"
                + "Nome do Autor:" + nomeAutor + "\n"
                + "Ano de Nascimento:" + anoNasc + "\n"
                + "Ano da Morte=" + anoMorte + "\n"
                + "----------------------------------";
    }



}
