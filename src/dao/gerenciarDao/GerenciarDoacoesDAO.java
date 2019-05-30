package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Doacao;

/**
 * Interface que define os métodos para gerenciar as doações no banco de dados.
 * @author Leonardo Elnisky
 */
public interface GerenciarDoacoesDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public void adicionar(String doacao, String recebedor) throws SQLException, IOException;
	public List<Doacao> load() throws SQLException, IOException;
}
