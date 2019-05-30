package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.PlacaVideo;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo PlacaVideo com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarPlacaVideoJDBCDAO implements GerenciarPlacaVideoDAO {
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
	 * Método para adicionar uma nova placa de vídeo ao banco de dados.
	 * @param placa modelo de placa de vídeo com os dados a serem inseridos.
	 * @param qtd quantidade de placas iguais a serem adicionadas.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(PlacaVideo placa, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO PlacaVideo (placaId, marca, modelo, doador) VALUES (DEFAULT, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, placa.getMarca());
		stmt.setString(2, placa.getModelo());
		stmt.setString(3, placa.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}
	
	/**
	 * Método para carregar todas as placas de vídeo do banco de dados.
	 * @return listVideos lista com todas as placas de vídeo contidas no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<PlacaVideo> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM PlacaVideo ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<PlacaVideo> listVideo = new ArrayList<>();
				while (rs.next()) {
					PlacaVideo video = new PlacaVideo();
					video.setPlacaId(rs.getInt("placaId"));
					video.setGabineteId(rs.getInt("gabineteId"));
					video.setNotebookId(rs.getInt("notebookId"));
					video.setMarca(rs.getString("marca"));
					video.setModelo(rs.getString("modelo"));
					video.setDoador(rs.getString("doador"));
					listVideo.add(video);
				}
				close();
				return listVideo;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de placas de vídeo do banco de dados.
	 * @param selecionados lista contendo as placas a serem removidas.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<PlacaVideo> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM PlacaVideo WHERE placaId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (PlacaVideo i : selecionados) {
			stmt.setInt(1, i.getPlacaId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
