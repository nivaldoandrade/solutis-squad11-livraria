package entities;

import java.util.Objects;

public abstract class Livro {
    private String titulo;

    private String autore;

    private String editora;

    private float preco;

    public Livro(String titulo, String autore, String editora, float preco) {
        this.titulo = titulo;
        this.autore = autore;
        this.editora = editora;
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format(
                "Título: %s%n" +
                "Autor: %s%n" +
                "Editor: %s%n" +
                "Preço: %.2f%n",
                titulo, autore,editora, preco
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Float.compare(preco, livro.preco) == 0 && Objects.equals(titulo, livro.titulo) && Objects.equals(autore, livro.autore) && Objects.equals(editora, livro.editora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, autore, editora, preco);
    }
}
