package com.Liter.Alura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String  nomeAutor;
    private Integer anoNasc;
    private Integer anoMorte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){

    }
    public Autor(DadosAutor dadosAutor){
        this.nomeAutor = dadosAutor.name();
        this.anoNasc = dadosAutor.anoNasc();
        this.anoMorte = dadosAutor.anoMorte();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return
                "Autor: " + nomeAutor + "\n"
                        + "Nascimento: " + anoNasc + "\n"
                        + "Falecimento: " + anoMorte + ""
                        + "Livros: " + livros ;
    }

}
