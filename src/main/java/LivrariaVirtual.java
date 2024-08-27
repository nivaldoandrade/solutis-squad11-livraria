import dao.LivroDao;
import dao.VendaDao;
import dao.impl.LivroDaoJDBC;
import dao.impl.VendaDaoJDBC;
import db.DB;
import services.LivroService;
import services.VendaService;

import java.util.Scanner;

import static utils.Prompts.promptOpcaoComNumero;

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

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("========= LIVRARIA VIRTUAL =========");

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
                    livrariaVirtual.vendaService.listarVendas(scanner);
                    break;
                case 5:
                    break;
            }
            if( opcaoLivraria == 5) {
                System.out.println();
                System.out.println("Encerrando o sistema...");
                break;
            }
        }

        DB.closeConnection();
    }

    private static int escolherOpcaoLivraria(Scanner scanner) {
        String promptInfo = "Opções da livraria: \n" +
                "1 - Cadastrar livro; \n" +
                "2 - Realizar venda; \n" +
                "3 - Listar livros; \n" +
                "4 - Listar vendas; \n" +
                "5 - Sair.";

        String promptOpcao = "Escolha a sua opção entre 1-5: ";

        return promptOpcaoComNumero(scanner,promptInfo, promptOpcao, 1, 5);
    }
}
