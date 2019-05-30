package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import model.Processador;

/**
 * Interface que define os métodos para Gerenciar Disco.
 * @author Leonardo Elnisky.
 */
public interface GerenciarProcessadorDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Processador processador, int qtd) throws SQLException, IOException;
	public List<Processador> load() throws SQLException, IOException;
	public int remover(ObservableList<Processador> selecionados) throws SQLException, IOException;
}
