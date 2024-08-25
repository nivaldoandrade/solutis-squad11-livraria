import entities.Eletronico;
import entities.Impresso;
import entities.Livro;
import entities.Venda;
import utils.LivroPadrao;
import utils.Prompts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LivrariaVirtual {
    private static final int MAX_IMPRESSOS = 10;
    private static final int MAX_ELETRONICOS = 20;
    private static final int MAX_VENDAS = 50;

    private final List<Impresso> impressos = List.of(
            new Impresso("O Sol é Para Todos", "Harper Lee", "J.P. Editores", 34.90f, 7.50f, 150),
            new Impresso("A Menina que Roubava Livros", "Markus Zusak", "Intrínseca", 39.90f, 6.00f, 200),
            new Impresso("Os Miseráveis", "Victor Hugo", "Martin Claret", 49.90f, 8.00f, 120),
            new Impresso("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "Agir", 19.90f, 5.00f, 0),
            new Impresso("O Senhor das Moscas", "William Golding", "Companhia das Letras", 29.90f, 4.50f, 90),
            new Impresso("Um Estranho em Casa", "Tana French", "HarperCollins", 44.90f, 7.00f, 110),
            new Impresso("O Caso dos 10 Negrinhos", "Agatha Christie", "Record", 31.90f, 6.50f, 95),
            new Impresso("O Código Da Vinci", "Dan Brown", "Suma de Letras", 54.90f, 10.00f, 0),
            new Impresso("A Garota no Trem", "Paula Hawkins", "Editora Planeta", 39.90f, 6.00f, 140),
            new Impresso("A Morte de Ivan Ilitch", "Leon Tolstói", "L&PM Pocket", 22.90f, 5.50f, 100)
    );

    private final List<Eletronico> eletronicos = List.of(
            new Eletronico("O Senhor dos Anéis", "J.R.R. Tolkien", "HarperCollins", 59.99f, 1024),
            new Eletronico("1984", "George Orwell", "Companhia das Letras", 39.99f, 512),
            new Eletronico("O Hobbit", "J.R.R. Tolkien", "HarperCollins", 29.99f, 2048),
            new Eletronico("A Revolução dos Bichos", "George Orwell", "Companhia das Letras", 19.99f, 256),
            new Eletronico("Orgulho e Preconceito", "Jane Austen", "Martin Claret", 25.99f, 128),
            new Eletronico("Dom Casmurro", "Machado de Assis", "Companhia das Letras", 35.99f, 2048),
            new Eletronico("O Guarani", "José de Alencar", "Martin Claret", 22.99f, 512),
            new Eletronico("Memórias Póstumas de Brás Cubas", "Machado de Assis", "Companhia das Letras", 18.99f, 1024),
            new Eletronico("O Primo Basílio", "José de Alencar", "Martin Claret", 20.99f, 256),
            new Eletronico("Iracema", "José de Alencar", "Companhia das Letras", 30.99f, 128)
    );
    private List<Venda> vendas = new ArrayList<>();

    private int numImpressos;
    private int numEletronicos;
    private int numVendas;

    public void cadastrarLivro(Scanner scanner) {
        System.out.println();
        System.out.println("====== CADASTRAR LIVRO ======");

        int opcaoTipoLivro = selecionarTipoDeLivro(scanner);

        Livro livro = null;
        Livro livro1 = null;

        switch (opcaoTipoLivro) {
            case 1:
                livro = criarLivroImpresso(scanner);
                break;
            case 2:
                livro = criarLivroEletronico(scanner);
                break;
            case 3:
                livro = criarLivroImpresso(scanner);
                livro1 = criarLivroEletronico(scanner);

        }

        if(livro != null) {
            System.out.println(livro.toString());
        }
        if(livro1 != null) {
         System.out.println(livro1.toString());
        }
    }

    public void realizarVenda(Scanner scanner) {
        System.out.println();
        System.out.println("====== VENDER LIVROS ======");

        String promptCliente = "Digite o nome do cliente: ";
        String cliente = Prompts.promptNameInput(scanner, promptCliente);

        String promptQtdLivros = "Digite a quantidade de livros que deseja comprar: ";
        int qtdLivros = Prompts.promptInt(scanner, promptQtdLivros);

        Venda venda = new Venda(cliente, qtdLivros);

        List<Livro> livros = selecionarLivrosAVenda(scanner, qtdLivros);

        int index = 0;
        for(Livro livro : livros) {
            venda.addLivro(livro, index++);

            if(livro instanceof  Impresso) {
                venda.incrementarValor(livro.getPreco() + ((Impresso) livro).getFrete());
                continue;
            }

            venda.incrementarValor(livro.getPreco());
        }

        venda.listarLivros();
    }

    private void listartLivrosImpressos() {
        System.out.println("====== LISTA DE LIVROS IMPRESSOS ======");
        int index = 0;

        for(Impresso livro : impressos) {
//            index += 1;

//            if(livro.getEstoque() == 0) {
//                continue;
//            }

            System.out.printf("====== LIVRO %02d ======%n", ++index);
            System.out.println(livro.toString());
        }
    }

    public void listarLivrosEletronicos() {
        System.out.println("====== LISTA DE LIVROS ELETRÔNICO ======");
        int index = 0;

        for(Eletronico livro: eletronicos) {
            System.out.printf("====== LIVRO %02d ======%n", ++index);
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

    public void listarVendas() {
        System.out.println();
        System.out.println("====== LISTAR VENDAS ======");

        for(Venda venda : vendas) {
            System.out.println(venda.toString());
        }
    }

    private List<Livro> selecionarLivrosAVenda(Scanner scanner, int qtdLivros) {
        List<Livro> livros = new ArrayList<>();

        for(int i = 1; i <= qtdLivros; i++) {
            System.out.println();
            System.out.printf("====== Escolhendo o tipo do entities.Livro %d ======%n", i);
            int opcaoTipoLivro = obterTipoLivro(scanner);
            Livro livroEscolhido = selecionarLivro(scanner, opcaoTipoLivro);
            livros.add(livroEscolhido);
        }

        return livros;
    }

    private int obterTipoLivro(Scanner scanner) {
        String promptInfo = "Tipos de livros: \n" +
                "1 - entities.Impresso; \n" +
                "2 - Eletrônico: ";
        String promptTipoLivro = "Conforme a lista acima, digite o tipo do livro: ";
        return Prompts.promptOpcaoComNumero(scanner, promptInfo, promptTipoLivro, 1, 2);
    }
    private Livro selecionarLivro(Scanner scanner, int tipoLivro) {
        if (tipoLivro == 1) {
            listartLivrosImpressos();
            return escolherLivro(scanner, impressos);
        } else {
            listarLivrosEletronicos();
           return  escolherLivro(scanner, eletronicos);
        }
    }

    private <T extends Livro> T escolherLivro(Scanner scanner, List<T> livros) {
        while (true) {
            String promptIndiceLivro = "Digite o número do livro da lista: ";
            int indexLivro = Prompts.promptInt(scanner, promptIndiceLivro);

            if(indexLivro < 1 || indexLivro > livros.size()) {
                System.out.print("Número do livro inválido! ");
                continue;
            }

            indexLivro -= 1;

            Livro livroSelecionado = livros.get(indexLivro);

            if(livroSelecionado instanceof Impresso && !temEstoqueLivro(indexLivro)) {
                System.out.print("Sem estoque, por favor escolha outro livro! ");
                continue;
            }

            return livros.get(indexLivro);
        }
    }

    private boolean temEstoqueLivro(int index) {
        return impressos.get(index).getEstoque() > 0;
    }

    private int selecionarTipoDeLivro(Scanner scanner) {
        String promptInfo = "Tipos de livros: \n" +
                "1 - entities.Impresso; \n" +
                "2 - Eletrônico; \n" +
                "3 - entities.Impresso e Eletrônico.";
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

        numImpressos += 1;

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

        numEletronicos += 1;

        return new Eletronico(
                livroPadrao.titulo(),
                livroPadrao.autor(),
                livroPadrao.editora(),
                livroPadrao.preco(),
                tamanho
        );
    }

    private boolean temEspacoImpresso() {
        return false;
//        return numImpressos < MAX_IMPRESSOS;
    }

    private boolean temEspacoEletronico() {
        return numEletronicos < MAX_ELETRONICOS;
    }

}
