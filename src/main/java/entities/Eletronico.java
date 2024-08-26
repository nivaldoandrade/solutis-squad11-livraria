package entities;

import java.util.Objects;

public class Eletronico extends Livro {

    private int tamanho;

    public Eletronico(String titulo, String autore, String editora, float preco, int tamanho) {
        super(titulo, autore, editora, preco);
        this.tamanho = tamanho;
    }

    public Eletronico(int id, String titulo, String autore, String editora, float preco, int tamanho) {
        super(id, titulo, autore, editora, preco);
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        String livroToString = super.toString();
        return livroToString + "tamanho: " + tamanho + "KB";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Eletronico that = (Eletronico) o;
        return tamanho == that.tamanho;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tamanho);
    }
}
