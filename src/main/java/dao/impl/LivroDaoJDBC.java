package dao.impl;

import db.DB;
import db.DBException;
import entities.Eletronico;
import entities.Impresso;
import entities.Livro;
import dao.LivroDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivroDaoJDBC implements LivroDao {

    private Connection conn = null;

    public LivroDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int totalImpressos() {
       Statement st = null;
       ResultSet rs = null;

       try {
           st = conn.createStatement();

           rs = st.executeQuery("SELECT COUNT(*) as total FROM Impresso");

           if(!rs.next()) {
               return 0;
           }

           return rs.getInt("total");

       } catch (SQLException e) {
           throw new DBException(e.getMessage());
       } finally {
           DB.closeStatement(st);
           DB.closeResultSet(rs);
       }
    }

    @Override
    public int totalEletronico() {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();

            rs = st.executeQuery("SELECT COUNT(*) as total FROM Eletronico");

            if(!rs.next()) {
                return 0;
            }

            return rs.getInt("total");

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Impresso> listarImpressos() {
        Statement st = null;
        ResultSet rs = null;

        List<Impresso> impressos = new ArrayList<>();

        String sql = "SELECT l.livro_id, l.titulo, l.autores, l.editora, l.preco, i.frete, i.estoque " +
                "FROM Livro l " +
                "JOIN Impresso i " +
                "ON i.livro_id = l.livro_id";

        try {
            st = conn.createStatement();

            rs = st.executeQuery(sql);

            Impresso impresso = null;

            while (rs.next()) {
                int livroId = rs.getInt("livro_id");
                String titulo = rs.getString("titulo");
                String autores = rs.getString("autores");
                String editora = rs.getString("editora");
                float preco = rs.getFloat("preco");
                float frete = rs.getFloat("frete");
                int estoque = rs.getInt("estoque");

                impresso = new Impresso(livroId, titulo, autores, editora ,preco, frete, estoque);

                impressos.add(impresso);
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return impressos;
    }

    @Override
    public List<Eletronico> listarEletronico() {
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT l.livro_id, l.titulo, l.autores, l.editora, l.preco, e.tamanho " +
                "FROM Livro l " +
                "JOIN Eletronico e " +
                "ON e.livro_id = l.livro_id";

        try {
            st = conn.createStatement();

            rs = st.executeQuery(sql);

            Eletronico eletronico = null;
            List<Eletronico> eletronicos = new ArrayList<>();

            while (rs.next()) {
                int livroId = rs.getInt("livro_id");
                String titulo = rs.getString("titulo");
                String autores = rs.getString("autores");
                String editora = rs.getString("editora");
                float preco = rs.getFloat("preco");
                int tamanho = rs.getInt("tamanho");

                eletronico = new Eletronico(livroId, titulo, autores, editora, preco, tamanho);

                eletronicos.add(eletronico);
            }

            return eletronicos;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public Optional<Impresso> buscarLivroImpressoPorId(int id) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        String sql = "SELECT l.livro_id, l.titulo, l.autores, l.editora, l.preco, i.frete, i.estoque " +
                "FROM Livro l " +
                "JOIN Impresso i " +
                "ON i.livro_id = l.livro_id " +
                "WHERE l.livro_id = ?";

        try {
            pst = conn.prepareStatement(sql);

            pst.setInt(1, id);

            rs = pst.executeQuery();

            if(!rs.next()) {
                return Optional.empty();
            }

            int livroId = rs.getInt("livro_id");
            String titulo = rs.getString("titulo");
            String autores = rs.getString("autores");
            String editora = rs.getString("editora");
            float preco = rs.getFloat("preco");
            float frete = rs.getFloat("frete");
            int estoque = rs.getInt("estoque");

            Impresso impresso = new Impresso(livroId, titulo, autores, editora, preco, frete, estoque);

            return Optional.of(impresso);
        } catch (SQLException e) {
            throw  new DBException(e.getMessage());
        }  finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Optional<Eletronico> buscarLivroEletronicoPorId(int id) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        String sql = "SELECT l.livro_id, l.titulo, l.autores, l.editora, l.preco, e.tamanho " +
                "FROM Livro l " +
                "JOIN Eletronico e " +
                "ON e.livro_id = l.livro_id " +
                "WHERE l.livro_id = ?";

        try {
            pst = conn.prepareStatement(sql);

            pst.setInt(1, id);

            rs = pst.executeQuery();

            if(!rs.next()) {
                return Optional.empty();
            }

            int livroId = rs.getInt("livro_id");
            String titulo = rs.getString("titulo");
            String autores = rs.getString("autores");
            String editora = rs.getString("editora");
            float preco = rs.getFloat("preco");
            int tamanho = rs.getInt("tamanho");

            Eletronico eletronico = new Eletronico(livroId, titulo, autores, editora, preco, tamanho);

            return Optional.of(eletronico);
        } catch (SQLException e) {
            throw  new DBException(e.getMessage());
        }  finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void atualizarEstoque(Impresso impresso) {
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(
                    "UPDATE Impresso " +
                            "SET estoque = ? " +
                            "WHERE livro_id = ?"
            );

            pst.setInt(1, impresso.getEstoque() - 1);
            pst.setInt(2, impresso.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }  finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void cadastrar(Livro livro) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO Livro " +
                            "(titulo, autores, editora, preco) " +
                            "VALUES (?, ?, ?, ? )",
                    Statement.RETURN_GENERATED_KEYS
            );

            pst.setString(1, livro.getTitulo());
            pst.setString(2, livro.getAutore());
            pst.setString(3, livro.getEditora());
            pst.setFloat(4, livro.getPreco());

            int rowsAffeceted = pst.executeUpdate();

            if(rowsAffeceted > 0) {
                rs = pst.getGeneratedKeys();

                if(rs.next()) {
                    int livroId = rs.getInt(1);

                    if(livro instanceof Impresso) {
                        cadastrarLivroImpresso(livroId, (Impresso) livro);
                    } else if(livro instanceof Eletronico) {
                        cadastrarLivroEletronico(livroId, (Eletronico) livro);
                    }

                    livro.setId(livroId);
                }
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(pst);

            if(rs != null) {
                DB.closeResultSet(rs);
            }
        }
    }

    private void cadastrarLivroImpresso(int livro_id, Impresso livro) throws SQLException {
        PreparedStatement pst = null;

        pst = conn.prepareStatement(
                "INSERT INTO Impresso " +
                        "(livro_id, frete, estoque) " +
                        "VALUES (?, ?, ?)"
        );

        pst.setInt(1, livro_id);
        pst.setFloat(2, livro.getFrete());
        pst.setFloat(3, livro.getEstoque());

        pst.executeUpdate();

    }

    private void cadastrarLivroEletronico(int livro_id, Eletronico livro) throws SQLException {
        PreparedStatement pst = null;

        pst = conn.prepareStatement(
                "INSERT INTO Eletronico " +
                        "(livro_id, tamanho) " +
                        "VALUES (?, ?)"
        );

        pst.setInt(1, livro_id);
        pst.setFloat(2, livro.getTamanho());

        pst.executeUpdate();
    }

}
