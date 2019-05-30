package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.Cabo;

/**
 * Interface que define os métodos para gerenciar o modelo Cabo.
 * @author Leonardo Elnisky
 */
public interface GerenciarCaboDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Cabo cabo) throws SQLException, IOException;
	public List<Cabo> load() throws SQLException, IOException;
	public Cabo buscar(String tipo) throws SQLException, IOException;
	public int remover(ObservableList<Cabo> selecionados, int qtd) throws SQLException, IOException;
}
