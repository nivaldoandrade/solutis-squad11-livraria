package utils;

import java.util.Scanner;

public final class Prompts {
    public static int promptInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();

                if(value > 0) {
                    return value;
                }

                System.out.print("Valor ter que ser maior do que zero! ");
            } else {
                System.out.print("Valor inválido! ");
                scanner.next();
            }
        }
    }

    public static String promptNameInput(Scanner scanner, String prompt) {
        String name;

        while(true) {
            System.out.print(prompt);
            name = scanner.nextLine();

            if(!name.isEmpty()) {
                return name.trim();
            }

            System.out.print("Nome não pode ser vazio! ");
        }
    }

    public static float promptFloat(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextFloat()) {
                float value = scanner.nextFloat();
                scanner.nextLine();

                if(value > 0) {
                    return value;
                }

                System.out.print("O valor não pode ser negativo! ");
            } else {
                System.out.print("Valor inválido! ");
                scanner.next();
            }
        }
    }

    public static int promptOpcaoComNumero(
            Scanner scanner,
            String promptInfo,
            String promptOpcao,
            int inicio,
            int ultimo
    ) {
        System.out.println(promptInfo);
        while (true) {
            int opcao = promptInt(scanner, promptOpcao);

            if(opcao >= inicio && opcao <= ultimo) {
                return opcao;
            }

            System.out.print("Opção inválida! ");
        }
    }

    public static char promptParaContinuar(Scanner scanner, String prompt) {
        char opcao;

        while(true) {
            System.out.print(prompt);
            opcao = Character.toLowerCase(scanner.next().charAt(0));
            scanner.nextLine();

            if( opcao == 'n' || opcao == 's') {
                break;
            }
            System.out.print("Selecione S para continuar ou N para cancelar! ");
        }
        return opcao;
    }
}
