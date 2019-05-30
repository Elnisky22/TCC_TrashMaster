package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.Fonte;

/**
 * Interface com a declaração dos métodos para gerenciar o modelo Fonte.
 * @author Leonardo Elnisky.
 */
public interface GerenciarFonteDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Fonte fonte, int qtd) throws SQLException, IOException;
	public List<Fonte> load() throws SQLException, IOException;
	public int remover(ObservableList<Fonte> selecionados) throws SQLException, IOException;
}
