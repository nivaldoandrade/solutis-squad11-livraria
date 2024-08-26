package dao;

import entities.Venda;

import java.util.List;

public interface VendaDao {

    void cadastrar(Venda venda);

    int totalVendas();

    List<Venda> listar();
}
