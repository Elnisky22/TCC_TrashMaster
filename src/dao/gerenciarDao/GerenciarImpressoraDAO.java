package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.Impressora;

/**
 * Interface que define os métodos para Gerenciar Impressora.
 * @author Leonardo Elnisky.
 */
public interface GerenciarImpressoraDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Impressora impressora, int qtd) throws SQLException, IOException;
	public List<Impressora> load() throws SQLException, IOException;
	public int remover(ObservableList<Impressora> selecionados) throws SQLException, IOException;
}
