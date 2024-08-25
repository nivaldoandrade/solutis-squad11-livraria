import entities.Livro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Database {

    private SessionFactory sessionFactory;
    private Session session;

    public Database() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
    }

    // Usar a funcao "db.encerrarConexao()" no final da classe Main!
    public void encerrarConexao() {
        if(sessionFactory != null) sessionFactory.close();
    }

    public <T extends Livro> void salvarLivro(T livro) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(livro);
                transaction.commit();
                System.out.println("Livro salvo com sucesso!\n");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                System.err.println("\nErro ao salvar livro: " + e.getMessage());
            }
        }
    }

    public <T extends Livro> void salvarListaDeLivros(List<T> livros) {
        for (T livro : livros) {
            salvarLivro(livro);
        }
    }

    /* Insere dados no banco de dados para testes
    public static void main(String[] args) {

        Database db = new Database();
        LivrariaVirtual livrariaVirtual = new LivrariaVirtual();

        List<Impresso> impressos = livrariaVirtual.impressos;
        List<Eletronico> eletronicos = livrariaVirtual.eletronicos;

        db.salvarListaDeLivros(impressos);
        db.salvarListaDeLivros(eletronicos);
        db.encerrarConexao();
    }
    */
}
