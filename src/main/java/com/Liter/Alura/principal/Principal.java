package com.Liter.Alura.principal;

import com.Liter.Alura.model.DadosApi;
import com.Liter.Alura.model.DadosLivro;
import com.Liter.Alura.model.Livro;
import com.Liter.Alura.repository.LivroRepository;
import com.Liter.Alura.service.ConsumoApi;
import com.Liter.Alura.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books?search=";

    Scanner leitura = new Scanner(System.in);
    private LivroRepository repositorio;

    public Principal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                       Bem Vindo a Biblioteca Virtual!
                       
                                 Menu
                    1 - Buscar Livro
                    2 - Listar Livros Cadastrados
                    3 - Listar Autores Cadastrados
                    4 - Listar Autores Vivos em um determinado ano
                    5 - Listar Livros em um determinado Idioma
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    System.out.println("Todos os Livro do Banco de Dados.\n");
                    listarLivro();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    System.out.println("Informe o Ano:");
                    int  ano = leitura.nextInt();
                    leitura.nextLine();
                    listarAutoresVivosNoAno(ano);
                    break;
                case 5:
                    System.out.println("Escolha um série pelo nome: ");
                    var idioma = leitura.nextLine();
                    listarLivrosPorIdioma(idioma);

                    break;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroWeb() {
        try {
            DadosLivro dadoslivro = getDadosLivro();

            Livro livro = new Livro(dadoslivro);
            repositorio.save(livro);
            System.out.println(dadoslivro);
        } catch (NullPointerException e) {

        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }
    private DadosLivro getDadosLivro() {

        System.out.println("Digite o nome da Livro para busca");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+") );
        DadosApi dadosApi = conversor.obterDados(json, DadosApi.class);

        if (dadosApi.results() != null && !dadosApi.results().isEmpty()) {
            return dadosApi.results().get(0);
        } else {
            System.out.println("Nenhum livro encontrado.");
            return null;
        }

    }

    private void listarLivro(){
        List<Livro> livros = repositorio.findAll();
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }
    private void buscarAutores() {
        List<String> autores = repositorio.findNomeAutor();
        for (String autor : autores) {
            System.out.println(" Autor:" + autor);
        }
    }
    public void listarAutoresVivosNoAno(int ano) {

        List<String> autores = repositorio.findAutoresVivosNoAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado no ano de " + ano);
        } else {
            System.out.println("Autores vivos no ano de " + ano + ":");
            autores.forEach(System.out::println);
        }
    }
    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = repositorio.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idioma);
        } else {
            System.out.println("Livros no idioma " + idioma + ":");
            livros.forEach(l -> System.out.println(l.getTitulo() + " - " + l.getNomeAutor()));
        }
    }


}
