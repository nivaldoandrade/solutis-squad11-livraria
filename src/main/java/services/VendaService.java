package services;

import dao.LivroDao;
import dao.VendaDao;
import entities.Eletronico;
import entities.Impresso;
import entities.Livro;
import entities.Venda;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static utils.Prompts.*;

public class VendaService {
    private static final int MAX_VENDAS = 50;
    private final VendaDao vendaDao;

    private final LivroDao livroDao;

    private final LivroService livroService;

    private int numVendas;


    public VendaService(VendaDao vendaDao, LivroDao livroDao, LivroService livroService) {
        this.vendaDao = vendaDao;
        this.livroDao = livroDao;
        this.livroService = livroService;
        numVendas = vendaDao.totalVendas();
        Venda.setNumVendas(numVendas);
    }

    public void realizarVenda(Scanner scanner) {
        if(!podeVender()) {
            System.out.println("Não é possivel vender o livro! O limite máximo foi atingido.");
            return;
        }
        System.out.println();
        System.out.println("====== VENDER LIVROS ======");

        String promptCliente = "Digite o nome do cliente: ";
        String cliente = promptNameInput(scanner, promptCliente);

        String promptQtdLivros = "Digite a quantidade de livros que deseja comprar: ";
        int qtdLivros = promptInt(scanner, promptQtdLivros);

        Venda venda = new Venda(cliente, qtdLivros);

        List<Livro> livros = selecionarLivrosAVenda(scanner, qtdLivros);

        String promptOpcaoCadastrar = "Confirmar a comprar: (s ou n): ";
        char opcaoCadastrar = promptParaContinuar(scanner, promptOpcaoCadastrar);

        if(opcaoCadastrar == 'n') {
            System.out.println("\n====== Compra cancelada ======\n");
            return;
        }

        for(Livro livro : livros) {
            venda.addLivro(livro);

            if(livro instanceof Impresso) {
                Impresso impresso = (Impresso) livro;
                venda.incrementarValor(livro.getPreco() + impresso.getFrete());
                livroDao.atualizarEstoque(impresso);
                continue;
            }

            venda.incrementarValor(livro.getPreco());
        }

        vendaDao.cadastrar(venda);


        venda.listarLivros();
    }



    public void listarVendas(Scanner scanner) {
        char continuar;

        while (true) {
            System.out.println();
            System.out.println("====== LISTAR VENDAS ======");
            System.out.println("Total de vendas: " + Venda.getNumVendas());

            List<Venda> vendas = vendaDao.listar();

            for(Venda venda : vendas) {
                venda.listarLivros();
            }

            String promptContinuar = "Deseja listar novamente as vendas? (s ou n): ";
            continuar = promptParaContinuar(scanner, promptContinuar);

            if( continuar == 'n') {
                System.out.println();
                break;
            }
        }

    }

    private List<Livro> selecionarLivrosAVenda(Scanner scanner, int qtdLivros) {
        List<Livro> livros = new ArrayList<>();

        for(int i = 1; i <= qtdLivros; i++) {
            System.out.println();
            System.out.printf("====== Escolhendo o tipo do Livro %d ======%n", i);
            int opcaoTipoLivro = obterTipoLivro(scanner);
            Livro livroEscolhido = selecionarLivro(scanner, opcaoTipoLivro);
            livros.add(livroEscolhido);
        }

        return livros;
    }

    private int obterTipoLivro(Scanner scanner) {
        String promptInfo = "Tipos de livros: \n" +
                "1 - Impresso; \n" +
                "2 - Eletrônico: ";
        String promptTipoLivro = "Conforme a lista acima, digite o tipo do livro: ";
        return promptOpcaoComNumero(scanner, promptInfo, promptTipoLivro, 1, 2);
    }

    private Livro selecionarLivro(Scanner scanner, int tipoLivro) {
        if (tipoLivro == 1) {
            livroService.listartLivrosImpressos();
            return escolherLivroImpresso(scanner);
        } else {
            livroService.listarLivrosEletronicos();
            return  escolherLivroEletronico(scanner);
        }
    }

    private Impresso escolherLivroImpresso(Scanner scanner) {
        while (true) {
            String promptLivroId = "Digite o número do livro da lista: ";
            int livroId = promptInt(scanner, promptLivroId);

            Optional<Impresso> livroSelecionado = livroDao.buscarLivroImpressoPorId(livroId);

            if(livroSelecionado.isEmpty()) {
                System.out.print("Número do livro inválido! ");
                continue;
            }

            if(livroSelecionado.get().getEstoque() == 0) {
                System.out.print("Sem estoque, por favor escolha outro livro! ");
                continue;
            }


            return livroSelecionado.get();
        }
    }

    private Eletronico escolherLivroEletronico(Scanner scanner) {
        while (true) {
            String promptLivroId = "Digite o número do livro da lista: ";
            int livroId = promptInt(scanner, promptLivroId);

            Optional<Eletronico> livroSelecionado = livroDao.buscarLivroEletronicoPorId(livroId);

            if(livroSelecionado.isEmpty()) {
                System.out.print("Número do livro inválido! ");
                continue;
            }

            return livroSelecionado.get();
        }
    }

    private boolean podeVender() {
        return  numVendas < MAX_VENDAS;
    }
}
