package dao;

import entities.Eletronico;
import entities.Impresso;
import entities.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroDao {

    void cadastrar(Livro livro);

    List<Impresso> listarImpressos();

    List<Eletronico> listarEletronico();

    Optional<Impresso> buscarLivroImpressoPorId(int livroId);

    Optional<Eletronico> buscarLivroEletronicoPorId(int livroId);

    void atualizarEstoque(Impresso impresso);

    int totalImpressos();

    int totalEletronico();

}
