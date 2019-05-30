package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.PlacaMae;

/**
 * Classe para gerenciar a comunica��o relacionada ao modelo Placa M�e com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarPlacaMaeJDBCDAO implements GerenciarPlacaMaeDAO {
	private Connection conexao;
	private PreparedStatement stmt;

	/**
	 * M�todo para abrir a conex�o com o banco de dados.
	 * @throws IOException 
	 */
	@Override
	public void open() throws SQLException, IOException {
		conexao = dao.Conexao.getConexao();
	}

	/**
	 * M�todo para fechar a conex�o com o banco de dados.
	 */
	@Override
	public void close() throws SQLException {
		conexao.close();
	}

	/**
	 * M�todo para adicionar uma nova placa m�e ao banco de dados.
	 * @param mae modelo de placa m�e com os dados a serem inseridas.
	 * @param qtd quantidade de placas m�e iguais a serem adicionadas.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(PlacaMae mae, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO PlacaMae (placaId, marca, modelo, socket, tiporam, doador) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, mae.getMarca());
		stmt.setString(2, mae.getModelo());
		stmt.setString(3, mae.getSocket());
		stmt.setString(4, mae.getRam());
		stmt.setString(5, mae.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}

	/**
	 * M�todo para carregar todas as placas m�e do banco de dados.
	 * @return listPlacas lista com todas as placas m�e contidas no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<PlacaMae> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM PlacaMae ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<PlacaMae> listPlacas = new ArrayList<>();
				while (rs.next()) {
					PlacaMae mae = new PlacaMae();
					mae.setPlacaId(rs.getInt("placaId"));
					mae.setGabineteId(rs.getInt("gabineteId"));
					mae.setNotebookId(rs.getInt("notebookId"));
					mae.setMarca(rs.getString("marca"));
					mae.setModelo(rs.getString("modelo"));
					mae.setSocket(rs.getString("socket"));
					mae.setRam(rs.getString("tiporam"));
					mae.setDoador(rs.getString("doador"));
					listPlacas.add(mae);
				}
				close();
				return listPlacas;
			}
		}
	}

	/**
	 * M�todo para remover (doar) uma lista de placas m�e do banco de dados.
	 * @param selecionados lista contendo as placas m�e a serem removidas.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<PlacaMae> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM PlacaMae WHERE placaId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (PlacaMae i : selecionados) {
			stmt.setInt(1, i.getPlacaId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
