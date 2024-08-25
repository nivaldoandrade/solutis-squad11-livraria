import utils.Prompts;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LivrariaVirtual livrariaVirtual = new LivrariaVirtual();
        System.out.println("========= LIVRARIA =========");

        while (true) {
            int opcaoLivraria = escolherOpcaoLivraria(scanner);

            switch (opcaoLivraria) {
                case 1:
                    livrariaVirtual.cadastrarLivro(scanner);
                    break;
                case 2:
                    livrariaVirtual.realizarVenda(scanner);
                    break;
                case 3:
                    livrariaVirtual.listarLivros(scanner);
                    break;
                case 4:
                    livrariaVirtual.listarVendas();
                    break;
            }

            char opcaoSair = promptParaContinuar(scanner);

            if(opcaoSair == 's') {
                System.out.println("Encerrando o sistema...");
                break;
            }
        }

    }

    private static int escolherOpcaoLivraria(Scanner scanner) {
        String promptInfo = "Opção da livraria: \n\n" +
                "1 - Cadastrar livro; \n" +
                "2 - Realizar venda; \n" +
                "3 - Listar livros; \n" +
                "4 - Listar vendas.";

        String promptOpcao = "\nEscolha a sua opção entre 1-4: ";

        return Prompts.promptOpcaoComNumero(scanner,promptInfo, promptOpcao, 1, 4);
    }

    private static char promptParaContinuar(Scanner scanner) {
        char opcao;

        while(true) {
            System.out.print("\nDeseja sair do sistema? (s ou n): ");
            opcao = Character.toLowerCase(scanner.next().charAt(0)) ;

            if( opcao == 'n' || opcao == 's') {
                break;
            }
            System.out.print("\nSelecione S para sair ou N para continuar! ");
        }
        return opcao;
    }
}
