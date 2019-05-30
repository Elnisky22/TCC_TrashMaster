package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import model.PlacaVideo;

/**
 * Interface que define os métodos para Gerenciar PlacaVideo.
 * @author Leonardo Elnisky.
 */
public interface GerenciarPlacaVideoDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(PlacaVideo placa, int qtd) throws SQLException, IOException;
	public List<PlacaVideo> load() throws SQLException, IOException;
	public int remover(ObservableList<PlacaVideo> selecionados) throws SQLException, IOException;
}
