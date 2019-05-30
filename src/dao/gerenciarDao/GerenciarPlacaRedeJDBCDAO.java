package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.PlacaRede;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo PlacaRede com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarPlacaRedeJDBCDAO implements GerenciarPlacaRedeDAO {
	private Connection conexao;
	private PreparedStatement stmt;
	
	/**
	 * Método para abrir a conexão com o banco de dados.
	 * @throws IOException 
	 */
	@Override
	public void open() throws SQLException, IOException {
		conexao = dao.Conexao.getConexao();
	}

	/**
	 * Método para fechar a conexão com o banco de dados.
	 */
	@Override
	public void close() throws SQLException {
		conexao.close();
	}

	/**
	 * Método para adicionar uma nova placa de rede ao banco de dados.
	 * @param placa modelo de placa de rede com os dados a serem inseridos.
	 * @param qtd quantidade de placas de rede iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(PlacaRede placa, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO PlacaRede (placaId, marca, modelo, wifi, doador) VALUES (DEFAULT, ?, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, placa.getMarca());
		stmt.setString(2, placa.getModelo());
		stmt.setBoolean(3, placa.getWifi());
		stmt.setString(4, placa.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}

	/**
	 * Método para carregar todos as placas de rede do banco de dados.
	 * @return listRedes lista com todas as placas de rede contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<PlacaRede> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM PlacaRede ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<PlacaRede> listRedes = new ArrayList<>();
				while (rs.next()) {
					PlacaRede rede = new PlacaRede();
					rede.setPlacaId(rs.getInt("placaId"));
					rede.setGabineteId(rs.getInt("gabineteId"));
					rede.setNotebookId(rs.getInt("notebookId"));
					rede.setMarca(rs.getString("marca"));
					rede.setModelo(rs.getString("modelo"));
					rede.setWifi(rs.getBoolean("wifi"));
					rede.setDoador(rs.getString("doador"));
					listRedes.add(rede);
				}
				close();
				return listRedes;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de placas de rede do banco de dados.
	 * @param selecionados lista contendo as placas de rede a serem removidas.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<PlacaRede> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM PlacaRede WHERE placaId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (PlacaRede i : selecionados) {
			stmt.setInt(1, i.getPlacaId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
