package com.Liter.Alura.principal;

import com.Liter.Alura.model.*;
import com.Liter.Alura.repository.AutorRepository;
import com.Liter.Alura.repository.LivroRepository;
import com.Liter.Alura.service.ConsumoApi;
import com.Liter.Alura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books?search=";

    Scanner leitura = new Scanner(System.in);
    private LivroRepository repositorio;
    private AutorRepository autorRepositorio;


    public Principal(LivroRepository repositorio, AutorRepository autorRepositorio) {
        this.repositorio = repositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                       Bem Vindo a Biblioteca Virtual!
                    
                                 Menu
                    1 - Buscar Livro - OK
                    2 - Listar Livros Cadastrados - OK
                    3 - Listar Autores Cadastrados
                    4 - Listar Autores Vivos em um determinado ano
                    5 - Listar Livros em um determinado Idioma
                    0 - Sair    
                    Escolha uma Opção;                            
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    System.out.println("Todos os Livros Cadastrados.\n");
                    listarLivro();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    System.out.println("Informe o Ano:");
                    int ano = leitura.nextInt();
                    leitura.nextLine();
                     listarAutoresVivosNoAno(ano);
                    break;
                case 5:
                    var menu2 = """
                               Buscar livro por um Idioma
                            pt - Português
                            en - Inglês
                            es - Espanhol
                            fr - Francês
                            Escolha uma Opção:
                            """;
                    System.out.println(menu2);
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

            if (dadoslivro == null) {
                System.out.println("Nenhum livro para salvar.");
                return;
            }
            Autor autor = null;

            if (dadoslivro.autor() != null && !dadoslivro.autor().isEmpty()) {
                DadosAutor dadosAutor = dadoslivro.autor().get(0);

                // Buscar autor no banco pelo nome
                Optional<Autor> autorExistente = autorRepositorio.findByNomeAutor(dadosAutor.name());

                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor();
                    autor.setNomeAutor(dadosAutor.name());
                    autor.setAnoNasc(dadosAutor.anoNasc());
                    autor.setAnoMorte(dadosAutor.anoMorte());
                    autor = autorRepositorio.save(autor);
                }
            }

            Livro livro = new Livro(dadoslivro);
            livro.setAutor(autor);

            if (autor != null) {
                autor.getLivros().add(livro);
            }
            repositorio.save(livro);

            System.out.println("-----------Livro------------");
            System.out.println("Titulo:" + dadoslivro.titulo());
            System.out.println("Autor:" + dadoslivro.autor().get(0).name());
            System.out.println("Idioma:" + dadoslivro.idiomas());
            System.out.println("Número de Downloand:" + dadoslivro.numeroDow());
            System.out.println();
            System.out.println("Livro e autor salvos com sucesso!");

        } catch (NullPointerException e) {
            System.out.println("Erro: Dados inválidos.");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private DadosLivro getDadosLivro() {
        System.out.println("Digite o nome do livro para busca:");
        var nomeLivro = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));


        DadosApi dadosApi = conversor.obterDados(json, DadosApi.class);

        if (dadosApi.results() != null && !dadosApi.results().isEmpty()) {
            return dadosApi.results().get(0);

        } else {
            System.out.println("Nenhum livro encontrado.");
            return null;
        }
    }

    private void listarLivro() {
        List<Livro> livros = repositorio.findAll();
        System.out.println("------Lista de Livros-------");
        System.out.println();
        for (Livro livro : livros) {
            System.out.println("-------- --Livro-------=----");
            System.out.println("Titulo:" + livro.getTitulo());
            System.out.println("Autor:" + (livro.getAutor()));
            System.out.println("Idioma:" + livro.getIdioma());
            System.out.println("Numero de Downloand:" + livro.getNumeroDow());
            System.out.println("----------------------------");
            System.out.println();
        }
    }

    private void buscarAutores() {
        List<Autor> autores = autorRepositorio.findAutores();
        System.out.println("-----Autores Cadastrados-------");
        System.out.println();
        for (Autor autor : autores) {
            System.out.println("Autor: " + autor.getNomeAutor());
            System.out.println("Nascimento: " + autor.getAnoNasc());
            System.out.println("Falecimento: " + autor.getAnoMorte());

            System.out.print("Livros: ");
            if (autor.getLivros() != null && !autor.getLivros().isEmpty()) {
                for (Livro livro : autor.getLivros()) {
                    System.out.print(livro.getTitulo() + ", ");
                    System.out.println();
                }
                System.out.println();
            }
        }
    }
        public void listarAutoresVivosNoAno(int ano) {
            List<Autor> autores = autorRepositorio.findAutoresVivosNoAno(ano);
                 System.out.println("Autores Vivos no ano de " + ano);
                 System.out.println();
            for (Autor autor : autores) {
                System.out.println("Autor: " + autor.getNomeAutor());
                System.out.println("Nascimento: " + autor.getAnoNasc());
                System.out.println("Falecimento: " + autor.getAnoMorte());

                if (autor.getLivros() != null && !autor.getLivros().isEmpty()) {
                    for (Livro livro : autor.getLivros()) {
                        System.out.println("Título do livro: " + livro.getTitulo());
                    }
                }
                System.out.println("------------------------------------");
            }
        }

    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livrosIdioma = repositorio.findByIdioma(idioma);
        if (livrosIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idioma);
        }
        if (idioma.equalsIgnoreCase("pt")){
            System.out.println("Livros no idioma Português:");
            System.out.println();
            livrosIdioma.forEach(l -> {
                    Autor autor = l.getAutor();
                    String autorStr = autor != null
                            ? autor.getNomeAutor() : "Autor não informado";

                    System.out.println("Titulo: " + l.getTitulo());
                    System.out.println("Autor: " + autorStr);
                    System.out.println("Idioma: " + l.getIdioma());
                    System.out.println("Numero de Downloads: " + l.getNumeroDow());
                    System.out.println("-----------------------------------");
                });

        }
        if (idioma.equalsIgnoreCase("en")) {
            System.out.println("Livros no idioma Inglês:");
            System.out.println();
            livrosIdioma.forEach(l -> {
                Autor autor = l.getAutor();
                String autorStr = autor != null
                        ? autor.getNomeAutor() : "Autor não informado";

                System.out.println("Titulo: " + l.getTitulo());
                System.out.println("Autor: " + autorStr);
                System.out.println("Idioma: " + l.getIdioma());
                System.out.println("Numero de Downloads: " + l.getNumeroDow());
                System.out.println("-----------------------------------");
            });
        }
        if (idioma.equalsIgnoreCase("es")) {
            System.out.println("Livros no idioma Espanhol:");
            System.out.println();
            livrosIdioma.forEach(l -> {
                Autor autor = l.getAutor();
                String autorStr = autor != null
                        ? autor.getNomeAutor() : "Autor não informado";

                System.out.println("Titulo: " + l.getTitulo());
                System.out.println("Autor: " + autorStr);
                System.out.println("Idioma: " + l.getIdioma());
                System.out.println("Numero de Downloads: " + l.getNumeroDow());
                System.out.println("-----------------------------------");
            });
        }
        if (idioma.equalsIgnoreCase("fr")) {
            System.out.println("Livros no idioma Francês:");
            System.out.println();
            livrosIdioma.forEach(l -> {
                Autor autor = l.getAutor();
                String autorStr = autor != null
                        ? autor.getNomeAutor() : "Autor não informado";

                System.out.println("Titulo: " + l.getTitulo());
                System.out.println("Autor: " + autorStr);
                System.out.println("Idioma: " + l.getIdioma());
                System.out.println("Numero de Downloads: " + l.getNumeroDow());
                System.out.println("-----------------------------------");
            });
        }
    }

}