package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.Gabinete;

/**
 * Interface que define os métodos para Gerenciar Gabinete.
 * @author Leonardo Elnisky.
 */
public interface GerenciarGabineteDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Gabinete gabinete, int qtd) throws SQLException, IOException;
	public List<Gabinete> load() throws SQLException, IOException;
	public int remover(ObservableList<Gabinete> selecionados) throws SQLException, IOException;
}
