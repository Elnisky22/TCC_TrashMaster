package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.Memoria;

/**
 * Interface que define os métodos para Gerenciar Memória.
 * @author Leonardo Elnisky.
 */
public interface GerenciarMemoriaDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Memoria memoria, int qtd) throws SQLException, IOException;
	public List<Memoria> load() throws SQLException, IOException;
	public int remover(ObservableList<Memoria> selecionados) throws SQLException, IOException;
}
