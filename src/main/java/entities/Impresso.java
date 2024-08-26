package entities;

import java.util.Objects;

public class Impresso extends Livro {

    private float frete;

    private int estoque;

    public Impresso(String titulo, String autores, String editora, float preco, float frete, int estoque) {
        super(titulo, autores, editora, preco);
        this.frete = frete;
        this.estoque = estoque;
    }

    public Impresso(int id, String titulo, String autores, String editora, float preco, float frete, int estoque) {
        super(id, titulo, autores, editora, preco);
        this.frete = frete;
        this.estoque = estoque;
    }

    public float getFrete() {
        return frete;
    }

    public void setFrete(float frete) {
        this.frete = frete;
    }

    public int getEstoque() {
        return estoque;
    }

    public void atualizarEstoque() {
        this.estoque -= 1;
    }

    @Override
    public String toString() {
        String livroToString = super.toString();
        String impresso = String.format(
                "Frete: %.2f%n" +
                "Estoque: %d",
               frete, estoque
        );
        return livroToString + impresso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Impresso impresso = (Impresso) o;
        return Float.compare(frete, impresso.frete) == 0 && estoque == impresso.estoque;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), frete, estoque);
    }
}
