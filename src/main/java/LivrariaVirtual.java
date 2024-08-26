import dao.LivroDao;
import dao.VendaDao;
import dao.impl.LivroDaoJDBC;
import dao.impl.VendaDaoJDBC;
import db.DB;
import services.LivroService;
import services.VendaService;
import utils.Prompts;

import java.util.Scanner;

public class LivrariaVirtual {
    private final LivroService livroService;
    private final VendaService vendaService;

    public LivrariaVirtual() {
        LivroDao livroDao = new LivroDaoJDBC(DB.getConnection());
        VendaDao vendaDao  = new VendaDaoJDBC(DB.getConnection());
        this.livroService = new LivroService(livroDao);
        this.vendaService = new VendaService(vendaDao, livroDao, livroService);
    }

    public static void main(String[] args) {
        DB.initializeDatabase();
        LivrariaVirtual livrariaVirtual = new LivrariaVirtual();

        System.out.println("========= LIVRARIA =========");
        Scanner scanner = new Scanner(System.in);


        while (true) {
            int opcaoLivraria = escolherOpcaoLivraria(scanner);

            switch (opcaoLivraria) {
                case 1:
                    livrariaVirtual.livroService.cadastrarLivro(scanner);
                    break;
                case 2:
                    livrariaVirtual.vendaService.realizarVenda(scanner);
                    break;
                case 3:
                    livrariaVirtual.livroService.listarLivros(scanner);
                    break;
                case 4:
                    livrariaVirtual.vendaService.listarVendas();
                    break;
            }

            char opcaoSair = promptParaContinuar(scanner);

            if(opcaoSair == 's') {
                System.out.println("Encerrando o sistema...");
                break;
            }
        }

        DB.closeConnection();
    }

    private static int escolherOpcaoLivraria(Scanner scanner) {
        String promptInfo = "Opção da livraria: \n" +
                "1 - Cadastrar livro; \n" +
                "2 - Realizar venda; \n" +
                "3 - Listar livros; \n" +
                "4 - Listar vendas.";

        String promptOpcao = "Escolha a sua opção entre 1-4: ";

        return Prompts.promptOpcaoComNumero(scanner,promptInfo, promptOpcao, 1, 4);
    }

    private static char promptParaContinuar(Scanner scanner) {
        char opcao;

        while(true) {
            System.out.print("Deseja sair do sistema? (s ou n): ");
            opcao = Character.toLowerCase(scanner.next().charAt(0)) ;

            if( opcao == 'n' || opcao == 's') {
                break;
            }
            System.out.print("Selecione S para sair ou N para continuar! ");
        }
        return opcao;
    }
}
