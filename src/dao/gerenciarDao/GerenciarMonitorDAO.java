package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import model.Monitor;

/**
 * Interface que define os métodos para Gerenciar Monitor.
 * @author Leonardo Elnisky.
 */
public interface GerenciarMonitorDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Monitor monitor, int qtd) throws SQLException, IOException;
	public List<Monitor> load() throws SQLException, IOException;
	public int remover(ObservableList<Monitor> selecionados) throws SQLException, IOException;
}
