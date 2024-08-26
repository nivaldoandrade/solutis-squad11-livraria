package dao;

import dao.impl.LivroDaoJDBC;
import db.DB;
import dao.impl.VendaDaoJDBC;

public class DaoFactory {

    public static LivroDao criarLivroDAO() {
        return new LivroDaoJDBC(DB.getConnection());
    }

    public static  VendaDao criarVendaDAO() {
        return new VendaDaoJDBC(DB.getConnection());
    }
}
