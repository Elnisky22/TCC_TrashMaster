package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import model.PlacaRede;

/**
 * Interface que define os métodos para Gerenciar PlacaRede.
 * @author Leonardo Elnisky.
 */
public interface GerenciarPlacaRedeDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(PlacaRede placa, int qtd) throws SQLException, IOException;
	public List<PlacaRede> load() throws SQLException, IOException;
	public int remover(ObservableList<PlacaRede> selecionados) throws SQLException, IOException;
}
