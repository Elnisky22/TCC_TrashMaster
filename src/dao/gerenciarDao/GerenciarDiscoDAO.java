package dao.gerenciarDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import model.DiscoRigido;

/**
 * Interface que define os métodos para Gerenciar Disco.
 * @author Leonardo Elnisky.
 */
public interface GerenciarDiscoDAO {
	public void open() throws SQLException, IOException;
	public void close() throws SQLException;
	public int adicionar(DiscoRigido disco, int qtd) throws SQLException, IOException;
	public List<DiscoRigido> load() throws SQLException, IOException;
	public int remover(ObservableList<DiscoRigido> selecionados) throws SQLException, IOException;
}
