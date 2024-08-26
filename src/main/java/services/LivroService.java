package services;

import dao.LivroDao;
import entities.Eletronico;
import entities.Impresso;
import utils.LivroPadrao;
import utils.Prompts;

import java.util.List;
import java.util.Scanner;

public class LivroService {
    private static final int MAX_IMPRESSOS = 10;

    private static final int MAX_ELETRONICOS = 20;

    private final LivroDao livroDao;

//    private final List<Impresso> impressos;
//
//    private final List<Eletronico> eletronicos;

//    private int numImpressos;
//
//    private int numEletronicos;

    public LivroService(LivroDao livroDao) {
        this.livroDao = livroDao;
//        this.impressos = livroDao.listarImpressos();
//        this.eletronicos = livroDao.listarEletronico();
//        this.numImpressos = livroDao.totalImpressos();
//        this.numEletronicos = livroDao.totalEletronico();
    }

    public void cadastrarLivro(Scanner scanner) {
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

        }

        if(impresso != null) {
            livroDao.cadastrar(impresso);
//            impressos.add(impresso);
        }
        if(eletronico != null) {
            livroDao.cadastrar(eletronico);
//            eletronicos.add(eletronico);
        }

        System.out.println("\n====== Livro(s) cadastrados com sucesso! ======\n");
    }

    public void listartLivrosImpressos() {
        System.out.println("====== LISTA DE LIVROS IMPRESSOS ======");

        List<Impresso> impressos = livroDao.listarImpressos();

        for(Impresso livro : impressos) {
            System.out.printf("====== LIVRO %02d ======%n", livro.getId());
            System.out.println(livro.toString());
        }
    }

    public void listarLivrosEletronicos() {
        System.out.println("====== LISTA DE LIVROS ELETRÔNICO ======");

        List<Eletronico> eletronicos = livroDao.listarEletronico();

        for(Eletronico livro: eletronicos) {
            System.out.printf("====== LIVRO %02d ======%n", livro.getId());
            System.out.println(livro.toString());
        }
    }

    public void listarLivros(Scanner scanner) {
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
        }
    }

    private int selecionarTipoDeLivro(Scanner scanner) {
        String promptInfo = "Tipos de livros: \n" +
                "1 - Impresso; \n" +
                "2 - Eletrônico; \n" +
                "3 - Impresso e Eletrônico.";
        String promptOpcao = "Qual tipo de livro que será cadastrado: ";
        return Prompts.promptOpcaoComNumero(scanner, promptInfo, promptOpcao, 1, 3);
    };

    private LivroPadrao criarLivroPadrao(Scanner scanner) {
        String promptTitulo = "Qual o titulo do livro? ";
        String titulo = Prompts.promptNameInput(scanner, promptTitulo);

        String promptAutor = "Digite o nome do(a) autor(a): ";
        String autor = Prompts.promptNameInput(scanner, promptAutor);

        String promptEditora = "Digite o nome da editora: ";
        String editora = Prompts.promptNameInput(scanner, promptEditora);

        String promptPreco = "Digite o preço do livro: ";
        float preco = Prompts.promptFloat(scanner, promptPreco);

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
        float frete = Prompts.promptFloat(scanner, promptFrete);

        String promptEstoque = "Digite a quantidade de livros em estoque: ";
        int estoque = Prompts.promptInt(scanner, promptEstoque);

//        numImpressos += 1;

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
        int tamanho = Prompts.promptInt(scanner, promptTamanho);

//        numEletronicos += 1;

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
