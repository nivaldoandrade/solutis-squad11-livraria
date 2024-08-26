package entities;

import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;

    private List<Livro> livros = new ArrayList<>();

    private static int numVendas = 0;

    private int numero;

    private String cliente;

    private float valor = 0F;

    public Venda(int numero, String cliente, float valor) {
        this.numero = numero;
        this.cliente = cliente;
        this.valor = valor;
    }

    public Venda(String cliente, int qtdLivros) {
        this.cliente = cliente;
        numVendas++;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Livro> getLivros() {

        return livros;
    }

    public static void setNumVendas(int numVendas) {
        Venda.numVendas = numVendas;
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

    public void addLivro(Livro livro) {
        livros.add(livro);
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

        System.out.printf("Quantidade de livros vendidos: %d%n", livros.size());
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
