package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.LeitorDisco;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Leitor de Discos com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarLeitorJDBCDAO implements GerenciarLeitorDAO {
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
	 * Método para adicionar um novo leitor ao banco de dados.
	 * @param leitor modelo de leitor com os dados a serem inseridos.
	 * @param qtd quantidade de leitores iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(LeitorDisco leitor, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO LeitorDisco (leitorId, marca, modelo, doador) VALUES (DEFAULT, ?, ?, ?)";
	
		open();
		stmt = conexao.prepareStatement(sql);	
		stmt.setString(1, leitor.getMarca());
		stmt.setString(2, leitor.getModelo());
		stmt.setString(3, leitor.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}

	/**
	 * Método para carregar todos os leitores do banco de dados.
	 * @return listLeitores lista com todos os leitores contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<LeitorDisco> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM LeitorDisco ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<LeitorDisco> listLeitores = new ArrayList<>();
				while (rs.next()) {
					LeitorDisco leitor = new LeitorDisco();
					leitor.setLeitorId(rs.getInt("leitorId"));
					leitor.setGabineteId(rs.getInt("gabineteId"));
					leitor.setNotebookId(rs.getInt("notebookId"));
					leitor.setMarca(rs.getString("marca"));
					leitor.setModelo(rs.getString("modelo"));
					leitor.setDoador(rs.getString("doador"));
					listLeitores.add(leitor);
				}
				close();
				return listLeitores;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de leitores do banco de dados.
	 * @param selecionados lista contendo os leitores a serem removidos.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<LeitorDisco> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM LeitorDisco WHERE leitorId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (LeitorDisco i : selecionados) {
			stmt.setInt(1, i.getLeitorId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}

}
