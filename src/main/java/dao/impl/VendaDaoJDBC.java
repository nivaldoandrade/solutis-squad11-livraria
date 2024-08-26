package dao.impl;

import dao.VendaDao;
import db.DB;
import db.DBException;
import entities.Eletronico;
import entities.Impresso;
import entities.Livro;
import entities.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendaDaoJDBC implements VendaDao {

    private Connection conn = null;

    public VendaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Venda> listar() {
        Statement st = null;
        ResultSet rs = null;

        List<Venda> vendas = new ArrayList<>();
        Map<Integer, Venda> vendaMap = new HashMap<>();

        try {
            st = conn.createStatement();

            rs = st.executeQuery("SELECT v.*, l.*, i.frete, i.estoque, e.tamanho, " +
                    "CASE WHEN i.livro_id IS NOT NULL THEN 'Impresso' " +
                    "WHEN e.livro_id IS NOT NULL THEN 'Eletronico' " +
                    "END AS tipo_livro " +
                    "FROM Venda v " +
                    "INNER JOIN Livro_Venda vl ON v.venda_id= vl.venda_id " +
                    "INNER JOIN Livro l ON vl.livro_id = l.livro_id " +
                    "LEFT JOIN Impresso i ON l.livro_id = i.livro_id " +
                    "LEFT JOIN Eletronico e ON l.livro_id = e.livro_id");

            while(rs.next()) {
                int vendaId = rs.getInt("venda_id");
                String cliente = rs.getString("cliente");
                float valor = rs.getFloat("valor");

                Venda venda = vendaMap.get(vendaId);

                if(venda == null) {
                    venda = new Venda(vendaId, cliente, valor);
                    vendas.add(venda);
                    vendaMap.put(vendaId, venda);
                }

                Livro livro;
                String tipoLivro = rs.getString("tipo_livro");

                int livroId = rs.getInt("livro_id");
                String titulo = rs.getString("titulo");
                String autores = rs.getString("autores");
                String editora = rs.getString("editora");
                float precoLivro = rs.getFloat("preco");

                if("Impresso".equals(tipoLivro)) {
                    float frete = rs.getFloat("frete");
                    int estoque = rs.getInt("estoque");

                    livro = new Impresso(livroId, titulo, autores, editora, precoLivro, frete, estoque);
                } else if("Eletronico".equals(tipoLivro)) {
                    int tamanho = rs.getInt("tamanho");

                    livro = new Eletronico(livroId, titulo, autores, editora, precoLivro, tamanho);
                } else {
                    continue;
                }

                venda.addLivro(livro);
            }

            return vendas;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public int totalVendas() {
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();

            rs = st.executeQuery("SELECT COUNT(*) as total FROM Venda");

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
    public void cadastrar(Venda venda) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement(
                    "INSERT INTO Venda " +
                            "(cliente, valor) " +
                            "VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            pst.setString(1, venda.getCliente());
            pst.setFloat(2, venda.getValor());

            int rowsAffected = pst.executeUpdate();

            if(rowsAffected > 0) {
                rs = pst.getGeneratedKeys();

                if(rs.next()) {
                    int vendaId = rs.getInt(1);
                    venda.setId(vendaId);

                    for (Livro i : venda.getLivros()) {
                       associarLivroAVenda(pst ,vendaId, i.getId());
                    }
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

    private void associarLivroAVenda(PreparedStatement pst, int vendaId, int livroId) throws SQLException {
        pst = conn.prepareStatement(
                "INSERT INTO Livro_Venda " +
                        "(livro_id, venda_id) " +
                        "VALUES (?, ?)"
        );
        pst.setInt(1, livroId);
        pst.setInt(2, vendaId);

        pst.executeUpdate();
    }
}
