package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.Outros;

/**
 * Interface que define os métodos para Gerenciar Outros.
 * @author Leonardo Elnisky.
 */
public interface GerenciarOutrosDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Outros outros, int qtd) throws SQLException, IOException;
	public List<Outros> load() throws SQLException, IOException;
	public int remover(ObservableList<Outros> selecionados) throws SQLException, IOException;
}
