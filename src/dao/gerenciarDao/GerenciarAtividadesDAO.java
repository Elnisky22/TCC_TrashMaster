package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Atividade;

/**
 * Interface que define os métodos para gerenciar o modelo Atividade.
 * @author Leonardo Elnisky
 */
public interface GerenciarAtividadesDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Atividade atividade) throws SQLException, IOException;
	public List<Atividade> load() throws SQLException, IOException;
}
