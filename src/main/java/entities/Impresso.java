package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Impresso extends Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private float frete;

    private int estoque;

    public Impresso(String titulo, String autore, String editora, float preco, float frete, int estoque) {
        super(titulo, autore, editora, preco);
        this.frete = frete;
        this.estoque = estoque;
    }

    public Impresso() {

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
