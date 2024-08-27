package services;

import dao.LivroDao;
import entities.Eletronico;
import entities.Impresso;
import utils.LivroPadrao;

import java.util.List;
import java.util.Scanner;

import static utils.Prompts.*;

public class LivroService {
    private static final int MAX_IMPRESSOS = 10;

    private static final int MAX_ELETRONICOS = 20;

    private final LivroDao livroDao;

    public LivroService(LivroDao livroDao) {
        this.livroDao = livroDao;
    }

    public void cadastrarLivro(Scanner scanner) {
        boolean continuarCadastro = true;

        while (continuarCadastro) {
            System.out.println();
            System.out.println("====== CADASTRAR LIVRO ======");

            int opcaoTipoLivro = selecionarTipoDeLivro(scanner);

            Impresso impresso = null;
            Eletronico eletronico = null;

            switch (opcaoTipoLivro) {
                case 1:
                    impresso = criarLivroImpresso(scanner);
                    break;
                case 2:
                    eletronico = criarLivroEletronico(scanner);
                    break;
                case 3:
                    impresso = criarLivroImpresso(scanner);
                    eletronico = criarLivroEletronico(scanner);
                    break;
                case 4:
                    System.out.println();
                    continuarCadastro = false;
                    continue;
            }

            if (impresso != null) {
                livroDao.cadastrar(impresso);
            }
            if (eletronico != null) {
                livroDao.cadastrar(eletronico);
            }
        }
    }

    public void listartLivrosImpressos() {
        System.out.println("====== LISTA DE LIVROS IMPRESSOS ======");

        List<Impresso> impressos = livroDao.listarImpressos();

        for(Impresso livro : impressos) {
            System.out.printf("====== LIVRO %02d ======%n", livro.getId());
            System.out.println(livro);
        }
    }

    public void listarLivrosEletronicos() {
        System.out.println("====== LISTA DE LIVROS ELETRÔNICO ======");

        List<Eletronico> eletronicos = livroDao.listarEletronico();

        for(Eletronico livro: eletronicos) {
            System.out.printf("====== LIVRO %02d ======%n", livro.getId());
            System.out.println(livro);
        }
    }

    public void listarLivros(Scanner scanner) {

        boolean continuarListar = true;

        while (continuarListar) {
            System.out.println();
            System.out.println("====== LISTAR LIVROS ======");
            int opcaoTipoLivro = selecionarTipoDeLivro(scanner);

            switch (opcaoTipoLivro) {
                case 1:
                    listartLivrosImpressos();
                    break;
                case 2:
                    listarLivrosEletronicos();
                    break;
                case 3:
                    listartLivrosImpressos();
                    System.out.println();
                    listarLivrosEletronicos();
                    break;
                case 4:
                    System.out.println();
                    continuarListar = false;
                    continue;
            }

            String promptContinuar = "Deseja listar outros livros? (s ou n): ";
            char continuar = promptParaContinuar(scanner, promptContinuar);

            if(!continuarListar || continuar == 'n') {
                System.out.println();
                break;
            }
        }

    }

    private int selecionarTipoDeLivro(Scanner scanner) {
        String promptInfo = "Tipos de livros: \n" +
                "1 - Impresso; \n" +
                "2 - Eletrônico; \n" +
                "3 - Impresso e Eletrônico; \n" +
                "4 - Retornar ao menu.";
        String promptOpcao = "Qual tipo de livro de livro: ";
        return promptOpcaoComNumero(scanner, promptInfo, promptOpcao, 1, 4);
    }

    private LivroPadrao criarLivroPadrao(Scanner scanner) {
        String promptTitulo = "Qual o titulo do livro: ";
        String titulo = promptNameInput(scanner, promptTitulo);

        String promptAutor = "Digite o nome do(a) autor(a): ";
        String autor = promptNameInput(scanner, promptAutor);

        String promptEditora = "Digite o nome da editora: ";
        String editora = promptNameInput(scanner, promptEditora);

        String promptPreco = "Digite o preço do livro: ";
        float preco = promptFloat(scanner, promptPreco);

        return new LivroPadrao(titulo, autor, editora, preco);
    }

    private Impresso criarLivroImpresso(Scanner scanner) {
        System.out.println();
        System.out.println("====== CADASTRANDO LIVRO IMPRESSO ======");
        if(!temEspacoImpresso()) {
            System.out.println("Não é possivel cadastrar livro impresso! O limite de espaço foi atingido");
            return null;
        }

        LivroPadrao livroPadrao = criarLivroPadrao(scanner);

        String promptFrete = "Digite o preço do frete: ";
        float frete = promptFloat(scanner, promptFrete);

        String promptEstoque = "Digite a quantidade de livros em estoque: ";
        int estoque = promptInt(scanner, promptEstoque);

        String promptOpcaoCadastrar = "Confirmar o cadastro do livro impresso: (s ou n): ";
        char opcaoCadastrar = promptParaContinuar(scanner,promptOpcaoCadastrar);

        if(opcaoCadastrar == 'n') {
            System.out.println("\n====== Cadastrado do livro impresso cancelado! ======\n");
            return null;
        }

        return new Impresso(
                livroPadrao.titulo(),
                livroPadrao.autor(),
                livroPadrao.editora(),
                livroPadrao.preco(),
                frete,
                estoque
        );
    }

    private Eletronico criarLivroEletronico(Scanner scanner) {
        System.out.println();
        System.out.println("====== CADASTRANDO LIVRO ELETRÔNICO ======");
        if(!temEspacoEletronico()) {
            System.out.println("Não é possivel cadastrar livro eletrônico! O limite de espaço foi atingido");
            return null;
        }
        LivroPadrao livroPadrao = criarLivroPadrao(scanner);

        String promptTamanho = "Digite o tamanho(KB) do livro: ";
        int tamanho = promptInt(scanner, promptTamanho);

        String promptOpcaoCadastrar = "Confirmar o cadastro do livro eletrônico: (s ou n): ";
        char opcaoCadastrar = promptParaContinuar(scanner, promptOpcaoCadastrar);

        if(opcaoCadastrar == 'n') {
            System.out.println("\n====== Cadastrado do livro eletrônico cancelado! ======\n");
            return null;
        }

        return new Eletronico(
                livroPadrao.titulo(),
                livroPadrao.autor(),
                livroPadrao.editora(),
                livroPadrao.preco(),
                tamanho
        );
    }

    private boolean temEspacoImpresso() {
        int numImpressos = livroDao.totalImpressos();
        return numImpressos < MAX_IMPRESSOS;
    }

    private boolean temEspacoEletronico() {
        int numEletronicos = livroDao.totalEletronico();
        return numEletronicos < MAX_ELETRONICOS;
    }

}
