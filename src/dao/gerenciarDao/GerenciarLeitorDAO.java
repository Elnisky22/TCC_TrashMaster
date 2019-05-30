package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.LeitorDisco;

/**
 * Interface que define os métodos para Gerenciar Leitor de Disco.
 * @author Leonardo Elnisky.
 */
public interface GerenciarLeitorDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(LeitorDisco leitor, int qtd) throws SQLException, IOException;
	public List<LeitorDisco> load() throws SQLException, IOException;
	public int remover(ObservableList<LeitorDisco> selecionados) throws SQLException, IOException;
}
