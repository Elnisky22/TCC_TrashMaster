package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Descarte;

/**
 * Interface que define os métodos para gerenciar o modelo Descarte.
 * @author Leonardo Elnisky
 */
public interface GerenciarDescartesDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public void adicionar(String itens) throws SQLException, IOException;
	public List<Descarte> load() throws SQLException, IOException;
	public void limpar() throws SQLException, IOException;
}
