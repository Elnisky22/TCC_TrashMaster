package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.PlacaMae;

/**
 * Interface que define os métodos para Gerenciar PlacaMae.
 * @author Leonardo Elnisky.
 */
public interface GerenciarPlacaMaeDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(PlacaMae mae, int qtd) throws SQLException, IOException;
	public List<PlacaMae> load() throws SQLException, IOException;
	public int remover(ObservableList<PlacaMae> selecionados) throws SQLException, IOException;
}
