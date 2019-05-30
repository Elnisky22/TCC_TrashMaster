package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.Mouse;

/**
 * Interface que define os métodos para Gerenciar Mouse.
 * @author Leonardo Elnisky.
 */
public interface GerenciarMouseDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Mouse mouse, int qtd) throws SQLException, IOException;
	public List<Mouse> load() throws SQLException, IOException;
	public int remover(ObservableList<Mouse> selecionados) throws SQLException, IOException;
}
