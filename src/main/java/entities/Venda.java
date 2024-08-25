package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    private Livro[] livros;

    private static int numVendas = 0;

    private int numero = numVendas;

    private String cliente;

    private float valor = 0F;

    public Venda(String cliente, int qtdLivros) {
        this.cliente = cliente;
        this.livros = new Livro[qtdLivros];
        numVendas++;
    }

    public Venda() {

    }

    public static int getNumVendas() {
        return numVendas;
    }

    public int getNumero() {
        return numero;
    }


    public String getCliente() {
        return cliente;
    }


    public float getValor() {
        return valor;
    }

    public void incrementarValor(float valor) {
        this.valor += valor;
    }

    public void addLivro(Livro livro, int index) {
        livros[index] = livro;
    }

    public void listarLivros() {
        System.out.println();
        System.out.printf("====== VENDA %02d ======%n", numero);
        System.out.println();
        System.out.println("INFORMAÇÃO DO CLIENTE: ");
        System.out.printf("Nome do cliente: %s%n", cliente);

        List<Impresso> impressos = obterListaPorTipo(Impresso.class);

        if(!impressos.isEmpty()) {
            System.out.println();
            System.out.println("LISTA DOS LIVROS IMPRESSOS: ");
            imprimirListaPorTipo(impressos);

        }

        List<Eletronico> eletronicos = obterListaPorTipo(Eletronico.class);

        if(!eletronicos.isEmpty()) {
            System.out.println();
            System.out.println("LISTA DOS LIVROS ELETRÔNICO: ");
            imprimirListaPorTipo(eletronicos);
        }

        System.out.printf("Quantidade de livros vendidos: %d%n", livros.length);
        System.out.printf("Valor total da venda: %.2f%n", valor);
    }

    private <T extends Livro> List<T> obterListaPorTipo(Class<T> tipoLivro) {
        List<T> listaLivros = new ArrayList<>();

        for (Livro livro : livros) {
            if(tipoLivro.isInstance(livro)) {
                listaLivros.add(tipoLivro.cast(livro));
            }
        }

        return listaLivros;
    }

    private <T extends Livro> void imprimirListaPorTipo(List<T> listaLivros) {
        int index = 0;

        for (Livro livro : listaLivros) {
            System.out.printf("====== LIVRO %02d ======%n", ++index);
            System.out.println(livro.toString());
            System.out.println();
        }
    }

}
