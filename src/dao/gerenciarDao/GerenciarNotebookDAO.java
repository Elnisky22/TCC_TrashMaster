package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.Notebook;

/**
 * Interface que define os métodos para Gerenciar Notebook.
 * @author Leonardo Elnisky.
 */
public interface GerenciarNotebookDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Notebook notebook, int qtd) throws SQLException, IOException;
	public List<Notebook> load() throws SQLException, IOException;
	public int remover(ObservableList<Notebook> selecionados) throws SQLException, IOException;
}
