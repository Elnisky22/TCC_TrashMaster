package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import model.Teclado;

/**
 * Interface que define os métodos para Gerenciar Teclado.
 * @author Leonardo Elnisky.
 */
public interface GerenciarTecladoDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(Teclado teclado, int qtd) throws SQLException, IOException;
	public List<Teclado> load() throws SQLException, IOException;
	public int remover(ObservableList<Teclado> selecionados) throws SQLException, IOException;
}
