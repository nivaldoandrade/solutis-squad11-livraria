package entities;

import java.util.Objects;

public abstract class Livro {
    private int id;

    private String titulo;

    private String autores;

    private String editora;

    private float preco;

    public Livro(String titulo, String autores, String editora, float preco) {
        this.titulo = titulo;
        this.autores = autores;
        this.editora = editora;
        this.preco = preco;
    }

    public Livro(int id, String titulo, String autores, String editora, float preco) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.editora = editora;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autore) {
        this.autores = autore;
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
                "Id: %d%n" +
                "Título: %s%n" +
                "Autor: %s%n" +
                "Editor: %s%n" +
                "Preço: %.2f%n",
                id, titulo, autores,editora, preco
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Float.compare(preco, livro.preco) == 0 && Objects.equals(titulo, livro.titulo) && Objects.equals(autores, livro.autores) && Objects.equals(editora, livro.editora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, autores, editora, preco);
    }
}
